// Qantas Proxy PAC

function FindProxyForURL(url, host)
{
    if (isPlainHostName(host))
	    return "DIRECT";
    
   	if (dnsDomainIs(host, ".qantas.com.au")
   	    || dnsDomainIs(host, ".qantas.com")) {

		return "DIRECT";
    }

    return TCIFProxy();
}

function TCIFProxy()
{
	//return "PROXY localhost:3128";
	return "PROXY qfwsg-prod.qantas.com.au:8080";
}

