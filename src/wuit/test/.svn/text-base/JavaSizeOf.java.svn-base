/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.test;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author lxl
 */
public class JavaSizeOf {
    private static Instrumentation inst;
    public static void premain(String agentArgs, Instrumentation inst) {
        JavaSizeOf.inst = inst;
    }
    /**
     * get size of java object.
     *
     * @param o
     * @return
     */
    public static long sizeof(Object o) {
        assert inst != null;
        Map<Object, Object> visited = new IdentityHashMap<Object, Object>();
        Stack<Object> visiting = new Stack<Object>();
        visiting.add(o);
        long size = 0;
        while (!visiting.isEmpty()) {
            size += analysis(visiting, visited);
        }
        return size;
    }
    /**
 58      * analysis java object size recursively.
 59      * 
 60      * @param visiting
 61      * @param visited
 62      * @return
 63      */
        protected static long analysis(Stack<Object> visiting, Map<Object, Object> visited) {
            Object o = visiting.pop();
            if (skip(o, visited)) {
                return 0;
            }
            visited.put(o, null);
            // array.
            if (o.getClass().isArray() && !o.getClass().getComponentType().isPrimitive()) {
     if (o.getClass().getName().length() != 2) {
                 for (int i = 0; i < Array.getLength(o); i++) {
                      visiting.add(Array.get(o, i));
                  }
             }
          }
          // object.
          else {
              Class<?> clazz = o.getClass();
              while (clazz != null) {
                  Field[] fields = clazz.getDeclaredFields();
                  for (Field field : fields) {
                      if (Modifier.isStatic(field.getModifiers())) {
                          continue;
                      }
                      if (field.getType().isPrimitive()) {
                          continue;
                      }
                      field.setAccessible(true);
                      try {
                          visiting.add(field.get(o));
                      } catch (Exception e) {
                         assert false;
                     }
                 }
                 clazz = clazz.getSuperclass();
              }
          }
         return inst.getObjectSize(o);
     }
 
    /**
104      * <pre>
105      * skip statistics.
106      * </pre>
107      * 
108      * @param o
109      * @param visited
110      * @return
111      */
    protected static boolean skip(Object o, Map<Object, Object> visited) {
         if (o instanceof String) {
             if (o == ((String) o).intern()) {
                 return true;
             }
         }
         return o == null || visited.containsKey(o);
     }
 
}
