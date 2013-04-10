package co.geographs.agrinsa.util;

import org.hibernate.type.Type;

public class Parameter {

	private String nameParameter;
	private Object valueParameter;
	private Type type;

	public Parameter() {

	}

	public Parameter(String nameParameter, Object valueParameter, Type type) {
		this.nameParameter = nameParameter;
		this.valueParameter = valueParameter;
		this.type = type;
	}

	public String getNameParameter() {
		return nameParameter;
	}

	public void setNameParameter(String nameParameter) {
		this.nameParameter = nameParameter;
	}

	public Object getValueParameter() {
		return valueParameter;
	}

	public void setValueParameter(Object valueParameter) {
		this.valueParameter = valueParameter;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
