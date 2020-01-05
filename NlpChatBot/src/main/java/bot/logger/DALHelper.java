/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
class DALHelper {
    
    
   static Properties getProperties() throws IOException {
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            String propertiesPath = System.getProperty("user.dir") + "\\db.properties";
            in = new FileInputStream(propertiesPath);
            props.load(in);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return props;
    }
}
