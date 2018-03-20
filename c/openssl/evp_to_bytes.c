#include <stdio.h>
#include <string.h>
#include <openssl/evp.h>

int main(void) {
    const EVP_CIPHER *cipher = NULL;
    const EVP_MD *digest = NULL;

    unsigned char key[EVP_MAX_KEY_LENGTH] = { 0 };
    unsigned char iv[EVP_MAX_IV_LENGTH] = { 0 };

    const char *password = "my-secret-password";
    const char *salt = NULL;

    OpenSSL_add_all_ciphers();
    OpenSSL_add_all_digests();

    if (!(cipher = EVP_get_cipherbyname("aes-256-cfb"))) {
        fprintf(stderr, "Cipher: %p\n", (void *)cipher);
        fprintf(stderr, "no such cipher\n");
        return 1;
    }

    if (!(digest = EVP_get_digestbyname("md5"))) {
        fprintf(stderr, "no such digest\n");
        return 1;
    }

    if (!EVP_BytesToKey(
        cipher, digest,
        (unsigned char *)salt, (unsigned char *)password,
        strlen(password), 1, key, iv)
    ) {
        fprintf(stderr, "EVP_BytesToKey failed\n");
        return 1;
    }

    printf("Key: ");
    for (int i = 0; i < cipher->key_len; ++i) {
        printf("%02x ", key[i]);
    }
    printf("\n");

    printf("IV: ");
    for (int j = 0; j < cipher->iv_len; ++j) {
        printf("%02x ", iv[j]);
    }
    printf("\n");

    return 0;
}
