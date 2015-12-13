package battle263;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ObjectSaver {
    
    public static boolean saveObject(Object o, String fileName) {
        boolean success = false;
        
        //won't this throw because oos.close() already closed fos?
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(o);
                success = true;
            }
        } catch(Exception e) {}
        
        return success;
    }
    
    public static Object loadObject(String fileName) {
        Object ret = null;
        
        try (FileInputStream fis = new FileInputStream(fileName);) {
            try (ObjectInputStream ois = new ObjectInputStream(fis);) {
                ret = ois.readObject();
            }
        } catch(Exception e) {}
        
        return ret;
    }
    
    
 
}
