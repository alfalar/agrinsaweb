package co.geographs.agrinsa.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

public class FacesUtil {
	
	public static void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		ExternalContext ectx = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession session = (HttpSession) ectx.getSession(false);
		SecurityContextHolder.clearContext();
		session.invalidate();
	}

	public static List<SelectItem> entityToSelectItem(List<?> list,
            String getIdMethod, String getDescMethod) throws Exception {
     List<SelectItem> items = new ArrayList<SelectItem>();

     Method idMethod = null;
     Method descMethod = null;

     for (int index = 0; index < list.size(); index++) {
            Object item = list.get(index);
            // On the first run, initialize reflection methods for object
            if (idMethod == null) {
                   Class<? extends Object> obj = item.getClass();
                   idMethod = obj.getMethod(getIdMethod, new Class[] {});
                   descMethod = obj.getMethod(getDescMethod, new Class[] {});
            }
            // invoke Methods
            String id = (String) idMethod.invoke(item, new Object[] {});
            String name = (String) descMethod.invoke(item, new Object[] {});            
            SelectItem selectItem = new SelectItem();            
            selectItem.setLabel(name);
            selectItem.setValue(id.toString());
            items.add(selectItem);
     }

     return items;
}
}
