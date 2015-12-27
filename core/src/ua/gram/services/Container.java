package ua.gram.services;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Container {

    private HashMap<String, Service> map;

    public Container(Service... services) {
        map = new HashMap<>(5);
        for (Service service : services) {
            map.put(service.getName(), service);
        }
    }

    public Service get(String name) {
        return map.get(name);
    }

}
