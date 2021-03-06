// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package oe.security3a.seupublic.client;

public final class ApplicationRmiImpl_Stub
    extends java.rmi.server.RemoteStub
    implements oe.security3a.client.rmi.ApplicationRmi, java.rmi.Remote
{
    private static final java.rmi.server.Operation[] operations = {
	new java.rmi.server.Operation("java.lang.String create(oe.security3a.seucore.obj.db.UmsApplication)"),
	new java.rmi.server.Operation("boolean drop(java.lang.Long)"),
	new java.rmi.server.Operation("boolean drops(java.lang.Long[])"),
	new java.rmi.server.Operation("oe.security3a.seucore.obj.db.UmsApplication loadObject(java.io.Serializable)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.security3a.seucore.obj.db.UmsApplication, java.util.Map)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.security3a.seucore.obj.db.UmsApplication, java.util.Map, int, int)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.security3a.seucore.obj.db.UmsApplication, java.util.Map, int, int, java.lang.String)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.security3a.seucore.obj.db.UmsApplication, java.util.Map, java.lang.String)"),
	new java.rmi.server.Operation("long queryObjectsNumber(oe.security3a.seucore.obj.db.UmsApplication, java.util.Map)"),
	new java.rmi.server.Operation("long queryObjectsNumber(oe.security3a.seucore.obj.db.UmsApplication, java.util.Map, java.lang.String)"),
	new java.rmi.server.Operation("boolean update(oe.security3a.seucore.obj.db.UmsApplication)"),
	new java.rmi.server.Operation("boolean updates(java.util.List)")
    };
    
    private static final long interfaceHash = -228715619929241265L;
    
    private static final long serialVersionUID = 2;
    
    private static boolean useNewInvoke;
    private static java.lang.reflect.Method $method_create_0;
    private static java.lang.reflect.Method $method_drop_1;
    private static java.lang.reflect.Method $method_drops_2;
    private static java.lang.reflect.Method $method_loadObject_3;
    private static java.lang.reflect.Method $method_queryObjects_4;
    private static java.lang.reflect.Method $method_queryObjects_5;
    private static java.lang.reflect.Method $method_queryObjects_6;
    private static java.lang.reflect.Method $method_queryObjects_7;
    private static java.lang.reflect.Method $method_queryObjectsNumber_8;
    private static java.lang.reflect.Method $method_queryObjectsNumber_9;
    private static java.lang.reflect.Method $method_update_10;
    private static java.lang.reflect.Method $method_updates_11;
    
    static {
	try {
	    java.rmi.server.RemoteRef.class.getMethod("invoke",
		new java.lang.Class[] {
		    java.rmi.Remote.class,
		    java.lang.reflect.Method.class,
		    java.lang.Object[].class,
		    long.class
		});
	    useNewInvoke = true;
	    $method_create_0 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("create", new java.lang.Class[] {oe.security3a.seucore.obj.db.UmsApplication.class});
	    $method_drop_1 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("drop", new java.lang.Class[] {java.lang.Long.class});
	    $method_drops_2 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("drops", new java.lang.Class[] {java.lang.Long[].class});
	    $method_loadObject_3 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("loadObject", new java.lang.Class[] {java.io.Serializable.class});
	    $method_queryObjects_4 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("queryObjects", new java.lang.Class[] {oe.security3a.seucore.obj.db.UmsApplication.class, java.util.Map.class});
	    $method_queryObjects_5 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("queryObjects", new java.lang.Class[] {oe.security3a.seucore.obj.db.UmsApplication.class, java.util.Map.class, int.class, int.class});
	    $method_queryObjects_6 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("queryObjects", new java.lang.Class[] {oe.security3a.seucore.obj.db.UmsApplication.class, java.util.Map.class, int.class, int.class, java.lang.String.class});
	    $method_queryObjects_7 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("queryObjects", new java.lang.Class[] {oe.security3a.seucore.obj.db.UmsApplication.class, java.util.Map.class, java.lang.String.class});
	    $method_queryObjectsNumber_8 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("queryObjectsNumber", new java.lang.Class[] {oe.security3a.seucore.obj.db.UmsApplication.class, java.util.Map.class});
	    $method_queryObjectsNumber_9 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("queryObjectsNumber", new java.lang.Class[] {oe.security3a.seucore.obj.db.UmsApplication.class, java.util.Map.class, java.lang.String.class});
	    $method_update_10 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("update", new java.lang.Class[] {oe.security3a.seucore.obj.db.UmsApplication.class});
	    $method_updates_11 = oe.security3a.client.rmi.ApplicationRmi.class.getMethod("updates", new java.lang.Class[] {java.util.List.class});
	} catch (java.lang.NoSuchMethodException e) {
	    useNewInvoke = false;
	}
    }
    
    // constructors
    public ApplicationRmiImpl_Stub() {
	super();
    }
    public ApplicationRmiImpl_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of create(UmsApplication)
    public java.lang.String create(oe.security3a.seucore.obj.db.UmsApplication $param_UmsApplication_1)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_create_0, new java.lang.Object[] {$param_UmsApplication_1}, -6108576458695565300L);
		return ((java.lang.String) $result);
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 0, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_UmsApplication_1);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		java.lang.String $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = (java.lang.String) in.readObject();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} catch (java.lang.ClassNotFoundException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of drop(Long)
    public boolean drop(java.lang.Long $param_Long_1)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_drop_1, new java.lang.Object[] {$param_Long_1}, -7673394856677527606L);
		return ((java.lang.Boolean) $result).booleanValue();
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 1, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_Long_1);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		boolean $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = in.readBoolean();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of drops(Long[])
    public boolean drops(java.lang.Long[] $param_arrayOf_Long_1)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_drops_2, new java.lang.Object[] {$param_arrayOf_Long_1}, -9637666104002331L);
		return ((java.lang.Boolean) $result).booleanValue();
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 2, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_arrayOf_Long_1);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		boolean $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = in.readBoolean();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of loadObject(Serializable)
    public oe.security3a.seucore.obj.db.UmsApplication loadObject(java.io.Serializable $param_Serializable_1)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_loadObject_3, new java.lang.Object[] {$param_Serializable_1}, -426776267883468910L);
		return ((oe.security3a.seucore.obj.db.UmsApplication) $result);
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 3, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_Serializable_1);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		oe.security3a.seucore.obj.db.UmsApplication $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = (oe.security3a.seucore.obj.db.UmsApplication) in.readObject();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} catch (java.lang.ClassNotFoundException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of queryObjects(UmsApplication, Map)
    public java.util.List queryObjects(oe.security3a.seucore.obj.db.UmsApplication $param_UmsApplication_1, java.util.Map $param_Map_2)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_queryObjects_4, new java.lang.Object[] {$param_UmsApplication_1, $param_Map_2}, 6166143396577890571L);
		return ((java.util.List) $result);
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 4, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_UmsApplication_1);
		    out.writeObject($param_Map_2);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		java.util.List $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = (java.util.List) in.readObject();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} catch (java.lang.ClassNotFoundException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of queryObjects(UmsApplication, Map, int, int)
    public java.util.List queryObjects(oe.security3a.seucore.obj.db.UmsApplication $param_UmsApplication_1, java.util.Map $param_Map_2, int $param_int_3, int $param_int_4)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_queryObjects_5, new java.lang.Object[] {$param_UmsApplication_1, $param_Map_2, new java.lang.Integer($param_int_3), new java.lang.Integer($param_int_4)}, 8080916371258680535L);
		return ((java.util.List) $result);
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 5, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_UmsApplication_1);
		    out.writeObject($param_Map_2);
		    out.writeInt($param_int_3);
		    out.writeInt($param_int_4);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		java.util.List $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = (java.util.List) in.readObject();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} catch (java.lang.ClassNotFoundException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of queryObjects(UmsApplication, Map, int, int, String)
    public java.util.List queryObjects(oe.security3a.seucore.obj.db.UmsApplication $param_UmsApplication_1, java.util.Map $param_Map_2, int $param_int_3, int $param_int_4, java.lang.String $param_String_5)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_queryObjects_6, new java.lang.Object[] {$param_UmsApplication_1, $param_Map_2, new java.lang.Integer($param_int_3), new java.lang.Integer($param_int_4), $param_String_5}, -8426392563491994229L);
		return ((java.util.List) $result);
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 6, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_UmsApplication_1);
		    out.writeObject($param_Map_2);
		    out.writeInt($param_int_3);
		    out.writeInt($param_int_4);
		    out.writeObject($param_String_5);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		java.util.List $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = (java.util.List) in.readObject();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} catch (java.lang.ClassNotFoundException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of queryObjects(UmsApplication, Map, String)
    public java.util.List queryObjects(oe.security3a.seucore.obj.db.UmsApplication $param_UmsApplication_1, java.util.Map $param_Map_2, java.lang.String $param_String_3)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_queryObjects_7, new java.lang.Object[] {$param_UmsApplication_1, $param_Map_2, $param_String_3}, 1452587720380996494L);
		return ((java.util.List) $result);
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 7, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_UmsApplication_1);
		    out.writeObject($param_Map_2);
		    out.writeObject($param_String_3);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		java.util.List $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = (java.util.List) in.readObject();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} catch (java.lang.ClassNotFoundException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of queryObjectsNumber(UmsApplication, Map)
    public long queryObjectsNumber(oe.security3a.seucore.obj.db.UmsApplication $param_UmsApplication_1, java.util.Map $param_Map_2)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_queryObjectsNumber_8, new java.lang.Object[] {$param_UmsApplication_1, $param_Map_2}, -1700657305252489745L);
		return ((java.lang.Long) $result).longValue();
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 8, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_UmsApplication_1);
		    out.writeObject($param_Map_2);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		long $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = in.readLong();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of queryObjectsNumber(UmsApplication, Map, String)
    public long queryObjectsNumber(oe.security3a.seucore.obj.db.UmsApplication $param_UmsApplication_1, java.util.Map $param_Map_2, java.lang.String $param_String_3)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_queryObjectsNumber_9, new java.lang.Object[] {$param_UmsApplication_1, $param_Map_2, $param_String_3}, -5951367860506672313L);
		return ((java.lang.Long) $result).longValue();
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 9, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_UmsApplication_1);
		    out.writeObject($param_Map_2);
		    out.writeObject($param_String_3);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		long $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = in.readLong();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of update(UmsApplication)
    public boolean update(oe.security3a.seucore.obj.db.UmsApplication $param_UmsApplication_1)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_update_10, new java.lang.Object[] {$param_UmsApplication_1}, -8438579685465702009L);
		return ((java.lang.Boolean) $result).booleanValue();
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 10, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_UmsApplication_1);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		boolean $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = in.readBoolean();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of updates(List)
    public boolean updates(java.util.List $param_List_1)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_updates_11, new java.lang.Object[] {$param_List_1}, 477322226957015029L);
		return ((java.lang.Boolean) $result).booleanValue();
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 11, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_List_1);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		boolean $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = in.readBoolean();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
