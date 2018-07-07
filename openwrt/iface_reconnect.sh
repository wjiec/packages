#!/bin/sh

I_IFACE=$1
ALL_IFACE=`ubus list | grep network.interface. | awk -F"." '{print $3}'`

EXISTS=`echo "${ALL_IFACE}" | grep "${I_IFACE}"`
if [[ "${EXISTS}" == "${I_IFACE}" ]]; then
    env -i /sbin/ifup "${I_IFACE}" >/dev/null 2>/dev/null
    echo "${I_IFACE} reconnected."
else
    echo "${I_IFACE} not exists"
fi
