// Skeleton class generated by rmic, do not edit.
// Contents subject to change without notice.

package oe.cms.service;

public final class CmsServiceImpl_Skel
    implements java.rmi.server.Skeleton
{
    private static final java.rmi.server.Operation[] operations = {
	new java.rmi.server.Operation("java.lang.String addpage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)"),
	new java.rmi.server.Operation("java.lang.String executeJpp(java.lang.String)"),
	new java.rmi.server.Operation("java.lang.String fetchJppScript(java.lang.String)"),
	new java.rmi.server.Operation("java.lang.String fetchJppTemplate(java.lang.String)")
    };
    
    private static final long interfaceHash = 5813498266049518140L;
    
    public java.rmi.server.Operation[] getOperations() {
	return (java.rmi.server.Operation[]) operations.clone();
    }
    
    public void dispatch(java.rmi.Remote obj, java.rmi.server.RemoteCall call, int opnum, long hash)
	throws java.lang.Exception
    {
	if (opnum < 0) {
	    if (hash == -1097201873299149206L) {
		opnum = 0;
	    } else if (hash == -1580433715200883771L) {
		opnum = 1;
	    } else if (hash == -139670711721671549L) {
		opnum = 2;
	    } else if (hash == 2933825051511617511L) {
		opnum = 3;
	    } else {
		throw new java.rmi.UnmarshalException("invalid method hash");
	    }
	} else {
	    if (hash != interfaceHash)
		throw new java.rmi.server.SkeletonMismatchException("interface hash mismatch");
	}
	
	oe.cms.service.CmsServiceImpl server = (oe.cms.service.CmsServiceImpl) obj;
	switch (opnum) {
	case 0: // addpage(String, String, String, String, String, String)
	{
	    java.lang.String $param_String_1;
	    java.lang.String $param_String_2;
	    java.lang.String $param_String_3;
	    java.lang.String $param_String_4;
	    java.lang.String $param_String_5;
	    java.lang.String $param_String_6;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
		$param_String_3 = (java.lang.String) in.readObject();
		$param_String_4 = (java.lang.String) in.readObject();
		$param_String_5 = (java.lang.String) in.readObject();
		$param_String_6 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.addpage($param_String_1, $param_String_2, $param_String_3, $param_String_4, $param_String_5, $param_String_6);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 1: // executeJpp(String)
	{
	    java.lang.String $param_String_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.executeJpp($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 2: // fetchJppScript(String)
	{
	    java.lang.String $param_String_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.fetchJppScript($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 3: // fetchJppTemplate(String)
	{
	    java.lang.String $param_String_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.fetchJppTemplate($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	default:
	    throw new java.rmi.UnmarshalException("invalid method number");
	}
    }
}
