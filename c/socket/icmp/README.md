icmpaddrmask
---
可以通过查看到有发送`ICMP`的`ICMP address mask request`
```shell
tcpdump -vv icmp 
```

> RFC 规定，除非系统是地址掩码的授权代理，否则不会发送地址掩码应答

