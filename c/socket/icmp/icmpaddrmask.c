#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <errno.h>
#include <time.h>
#include <netdb.h>

#include <sys/types.h>
#include <sys/param.h>
#include <sys/socket.h>
#include <sys/file.h>

#include <netinet/in.h>
#include <netinet/ip_icmp.h>
#include <arpa/inet.h>

#define DEF_DATA_LEN (12)
#define MAX_IP_LEN   60
#define MAX_ICMP_LEN 76
#define MAX_PACKET   (65535 - 60 - 8)

struct sockaddr_in address;
int response = 0;
int fd = 0;

void signal_alarm(int signo);
void sender(void);
unsigned short checksum(unsigned short *addr, int len);
void procPtk(char *buf, int cc, struct sockaddr_in *from);

int main(int argc, char *argv[]) {
    extern int errno;

    if (argc != 2) {
        fprintf(stderr, "Uaage: %s [hostname|ipaddr|domain].\n", argv[0]);
        exit(1);
    }

    const char *target = argv[1];
    extern struct sockaddr_in address;
    bzero(&address, sizeof(target));
    address.sin_family = AF_INET;
    inet_pton(AF_INET, target, &address.sin_addr);

    if (address.sin_addr.s_addr == 0) {
        struct hostent *hp = NULL;
        char hostname[32] = { 0 };

        if (!(hp = gethostbyname(target))) {
            fprintf(stderr, "Unknown host %s.\n", target);
            exit(1);
        }
        inet_ntop(hp->h_addrtype, *(hp->h_addr_list), hostname, 32);
        // printf("DNS Search: %s %s\n", hp->h_name, hostname);
        inet_pton(AF_INET, hostname, &address.sin_addr);
    }
    // printf("target address: %s\n", inet_ntoa(address.sin_addr));

    unsigned char *ptk = NULL;
    int ptkLen = DEF_DATA_LEN + MAX_IP_LEN + MAX_ICMP_LEN;
    if ((ptk = (unsigned char*)malloc(ptkLen)) == NULL) {
        fprintf(stderr, "malloc error.\n");
        exit(1);
    }

    struct protoent *proto = NULL;
    if ((proto = getprotobyname("icmp")) == NULL) {
        fprintf(stderr, "unknown protocol icmp");
        exit(1);
    }

    extern int fd;
    if ((fd = socket(AF_INET, SOCK_RAW, proto->p_proto)) < 0) {
        perror("socket");
        exit(1);
    }

    signal(SIGALRM, signal_alarm);
    alarm(5);

    sender();
    while (1) {
        struct sockaddr_in from;
        socklen_t len;
        int cc = 0;

        len = sizeof(from);
        if ((cc = recvfrom(fd, (char *)ptk, ptkLen, 0,(struct sockaddr*)&from, &len)) < 0) {
            if (errno == EINTR) {
                continue;
            } else {
                perror("recvfrom error");
                continue;
            }
            procPtk((char*)ptk, cc, &from);
            puts((char *)ptk);
        }
    }

    return 0;
}

void signal_alarm(int signo) {
    if (response == 0) {
        fprintf(stderr, "timeout.\n");
        exit(1);
    }
    exit(0);
}

void sender(void) {
    int ifd, cc;
    unsigned char oPtk[MAX_ICMP_LEN] = { 0 };
    struct icmp *icmp;

    icmp = (struct icmp*)oPtk;
    icmp->icmp_type = ICMP_MASKREQ;
    icmp->icmp_code = 0;
    icmp->icmp_cksum = 0;
    icmp->icmp_seq = 12345;
    icmp->icmp_id = getpid();
    
    icmp->icmp_mask = 0;
    
    cc = ICMP_MASKLEN;
    icmp->icmp_cksum = checksum((unsigned short*)icmp, cc);
    // printf("%d\n", icmp->icmp_cksum);
    
    ifd = sendto(fd, (char *)icmp, cc, 0, (struct sockaddr*)&address, sizeof(struct sockaddr));

    if (ifd < 0 || ifd != cc) {
        if (ifd < 0) {
            perror("sendto error");
        } else {
            fprintf(stderr, "%d chars sended, ret=%d", cc, ifd);
        }
    }
}

unsigned short checksum(unsigned short *addr, int len) {
    register int left = len;
    register unsigned short *curr = addr;
    register int sum = 0;
    unsigned short cksum = 0;

    while (left > 1) {
        sum += *curr++;
        left -= 2;
    }

    sum = (sum >> 16) + (sum & 0xffff);
    sum += (sum >> 16); // add again
    cksum = ~sum;
    return cksum;
}

void procptk(char *buf, int cc, struct sockaddr_in *from) {
    int len = 0;
    struct icmp *icmp;
    struct ip *ip;

    ip = (struct ip*)buf;
    len = ip->ip_hl << 2;
    if (cc < len + ICMP_MINLEN) {
        fprintf(stderr, "packet too short (%d bytes) from %s\n", cc, inet_ntoa(*(struct in_addr*)&from->sin_addr.s_addr));
        return;
    }

    cc -= len;
    icmp = (struct icmp*)(buf + len);
    if (icmp->icmp_type == ICMP_MASKREPLY) {
        if (cc != ICMP_MASKLEN) {
            printf("cc = %d, expected cc = 12\n", cc);
        }
        if (icmp->icmp_seq != 12345) {
            printf("received sequence #%d\n", icmp->icmp_seq);
        }
        if (icmp->icmp_id != getpid()) {
            printf("received id %d\n", icmp->icmp_id);
        }

        printf("received mask = %08x, from %s\n", ntohl(icmp->icmp_mask), inet_ntoa(*(struct in_addr*)&from->sin_addr.s_addr));

        response++;
    }
}


