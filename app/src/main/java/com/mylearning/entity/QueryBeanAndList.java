package com.mylearning.entity;

import java.io.Serializable;
import java.util.List;

public class QueryBeanAndList<T, O> implements Serializable{
	private static final long serialVersionUID = 2117592879992780291L;
	public List<T> list;
	public O bean;
}
