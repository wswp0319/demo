package demo.sql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class DSQ {


    @Autowired
    private JdbcTemplate jdbcTemplate;


//    @Scheduled(cron = "0/3 * * * * ?")
    @Test
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void baseInfoDriver() {
        System.out.println(jdbcTemplate);
//        String log4j = "D:\\idea\\demo\\src\\main\\webapp\\WEB-INF\\conf\\log4j.properties";
//
//        Properties properties = new Properties();
//        try {
//            properties.load(new InputStreamReader(new FileInputStream(log4j)));
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//        PropertyConfigurator.configure(properties);
//        Logger logger = Logger.getLogger(DSQ.class.getName());
//        logger.info("Load log4j config from " + log4j);


        String sqlUpdate = "update yy_passenger set mobile = 111111111 where uuid='01459847ec524b518562d5c9b72c4914'";


        jdbcTemplate.update(sqlUpdate);
//
//        if (true){
//            throw new RuntimeException();
//        }
    }
}
