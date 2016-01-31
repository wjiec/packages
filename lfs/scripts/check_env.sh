#!/bin/sh
C_START="\033[32;1m"
C_END="\033[0m"
E_WARNING="\033[31;1m"
E_NOTFOUND=$E_WARNING"Not Found"$C_END

echo "$C_START bash:\t\t$C_END`bash --version | head -n1 | cut -d' ' -f3-4`"
echo "$C_START /bin/sh:\t$C_END""hard link to `readlink -f /bin/sh`"
echo "$C_START binutils:\t$C_END`(hash ld && ld --version | head -n1 | cut -d" " -f3- || echo "$E_NOTFOUND") 2>/dev/null `"
if [ -h /usr/bin/yacc ]; then
    echo "$C_START yacc:\t\t$C_END""hard link to `readlink -f /usr/bin/yacc`"
elif [ -x /usr/bin/yacc ]; then
    echo "$C_START yacc:\t\t$C_END`/usr/bin/yacc --version | head -n1`"
else
    echo "$C_START yacc:\t\t$C_END"$E_NOTFOUND
fi
echo "$C_START bzip2:\t\t$C_END""`bzip2 --version 2>&1 < /dev/null | head -n1 | cut -d" " -f6- | sed 's/^.//g'`"
echo "$C_START coreutils:\t$C_END`(hash chown && chown --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START diff:\t\t$C_END`(hash diff && diff --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START find:\t\t$C_END`(hash find && find --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START gawk:\t\t$C_END`(hash gawk && gawk --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
if [ -h /usr/bin/awk ]; then
    echo "$C_START awk:\t\t$C_END""hard link to `readlink -f /usr/bin/awk`"
elif [ -x /usr/bin/awk ]; then
    echo "$C_START awk:\t\t$C_END`/usr/bin/awk --version | head -n1`"
else
    echo  "$C_START awk:\t\t$C_END""$E_WARNING Not Found$C_END"
fi
echo "$C_START gcc:\t\t$C_END`(hash gcc && gcc --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START g++:\t\t$C_END`(hash g++ && g++ --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START ldd:\t\t$C_END`(hash ldd && ldd --version | head -n1 | cut -d" " -f2- || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START grep:\t\t$C_END`(hash grep && grep --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START gzip:\t\t$C_END`(hash gzip && gzip --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START cat:\t\t$C_END`(hash cat && cat --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START m4:\t\t$C_END`(hash m4 && m4 --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START make:\t\t$C_END`(hash make && make --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START patch:\t\t$C_END`(hash patch && patch --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START perl:\t\t$C_END`(hash perl && perl -V:version || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START sed:\t\t$C_END`(hash sed && sed --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START tar:\t\t$C_END`(hash tar && tar --version | head -n1 || echo "$E_NOTFOUND") 2>/dev/null`"
echo "$C_START makeinfo:\t$C_END`(hash makeinfo && makeinfo --version | head -n1 || echo $E_NOTFOUND) 2>/dev/null`"
echo "$C_START xz:\t\t$C_END`(hash xz && xz --version | head -n1 || echo $E_NOTFOUND) 2>/dev/null`"
echo 'int main(){}' > dummy.c && (hash g++ && g++ -o dummy dummy.c || rm -f dummy.c) 2>/dev/null

if [ -x dummy ]; then
    echo " g++ Compilation OK"
else
    echo " g++ Compilation Failed"
fi
rm -f dummy dummy.c








