// Skeleton class generated by rmic, do not edit.
// Contents subject to change without notice.

package oe.bi.analysis;

public final class BiAnalysisImpl2_Skel
    implements java.rmi.server.Skeleton
{
    private static final java.rmi.server.Operation[] operations = {
	new java.rmi.server.Operation("oe.bi.view.obj.ViewModel performBiAnaysis(oe.bi.etl.obj.ChoiceInfo)")
    };
    
    private static final long interfaceHash = -1182905622735904539L;
    
    public java.rmi.server.Operation[] getOperations() {
	return (java.rmi.server.Operation[]) operations.clone();
    }
    
    public void dispatch(java.rmi.Remote obj, java.rmi.server.RemoteCall call, int opnum, long hash)
	throws java.lang.Exception
    {
	if (opnum < 0) {
	    if (hash == 8582650293018897748L) {
		opnum = 0;
	    } else {
		throw new java.rmi.UnmarshalException("invalid method hash");
	    }
	} else {
	    if (hash != interfaceHash)
		throw new java.rmi.server.SkeletonMismatchException("interface hash mismatch");
	}
	
	oe.bi.analysis.BiAnalysisImpl2 server = (oe.bi.analysis.BiAnalysisImpl2) obj;
	switch (opnum) {
	case 0: // performBiAnaysis(ChoiceInfo)
	{
	    oe.bi.etl.obj.ChoiceInfo $param_ChoiceInfo_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_ChoiceInfo_1 = (oe.bi.etl.obj.ChoiceInfo) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    oe.bi.view.obj.ViewModel $result = server.performBiAnaysis($param_ChoiceInfo_1);
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
