package com.stackroute.registrationserver.seedData;
import com.stackroute.registrationserver.domain.DeliveryBoys;
import com.stackroute.registrationserver.domain.Restaurants;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.stereotype.Component;
import com.stackroute.registrationserver.controller.RegistrationController;
import com.stackroute.registrationserver.domain.Charities;
import com.stackroute.registrationserver.service.RabbitService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class RestaurantSeedData implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    RegistrationController registrationController;

    @Autowired
    private RabbitService rabbitService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Restaurants restaurants=new Restaurants();
        try {

            FileInputStream file = new FileInputStream(new File("./charityseed.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(1);


            for (int i = 1; i < 14;i++) {

                int j = 0;

                restaurants.setName(workbook.getSheetAt(1).getRow(i).getCell(j).toString());
                restaurants.setUsername(workbook.getSheetAt(1).getRow(i).getCell(j + 1).toString());
                restaurants.setPassword(workbook.getSheetAt(1).getRow(i).getCell(j + 2).toString());
                restaurants.setEmail(workbook.getSheetAt(1).getRow(i).getCell(j + 3).toString());
                restaurants.setMobile((long)(Double.parseDouble(workbook.getSheetAt(1).getRow(i).getCell(j + 4).toString())));
                restaurants.setCertificateNo(workbook.getSheetAt(1).getRow(i).getCell(j + 5).toString());
                restaurants.setCertificateName(workbook.getSheetAt(1).getRow(i).getCell(j + 6).toString());
                restaurants.setLocation(workbook.getSheetAt(1).getRow(i).getCell(j + 7).toString());
                restaurants.setAddress(workbook.getSheetAt(1).getRow(i).getCell(j + 8).toString());
                restaurants.setRole(workbook.getSheetAt(1).getRow(i).getCell(j + 9).toString());
                System.out.println(restaurants);

                registrationController.saveRestaurant(restaurants);
                rabbitService.sendToRestaurantUpdateRabbitMq(restaurants);
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Charities charities=new Charities();
        try {

            FileInputStream file = new FileInputStream(new File("./charityseed.xlsx"));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            System.out.println("");



            for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                int j = 0;
                NumberFormat fmt = NumberFormat.getInstance(Locale.US);

                charities.setName(workbook.getSheetAt(0).getRow(i).getCell(j ).toString());
                charities.setUsername(workbook.getSheetAt(0).getRow(i).getCell(j + 1).toString());
                charities.setPassword(workbook.getSheetAt(0).getRow(i).getCell(j + 2).toString());
                charities.setAddress(workbook.getSheetAt(0).getRow(i).getCell(j + 3).toString());
                charities.setEmail(workbook.getSheetAt(0).getRow(i).getCell(j + 4).toString());
                charities.setMobile((long)(Double.parseDouble(workbook.getSheetAt(0).getRow(i).getCell(j + 5).toString())));
                charities.setCertificateNo(workbook.getSheetAt(0).getRow(i).getCell(j + 6).toString());
                charities.setCertificateName(workbook.getSheetAt(0).getRow(i).getCell(j + 7).toString());
                charities.setFoodRequirement((long)(Double.parseDouble(workbook.getSheetAt(0).getRow(i).getCell(j + 8).toString())));
                charities.setLocation(workbook.getSheetAt(0).getRow(i).getCell(j + 9).toString());
                charities.setRole(workbook.getSheetAt(0).getRow(i).getCell(j + 10).toString());
                System.out.println(charities);

                registrationController.saveCharity(charities);
                rabbitService.sendToCharityUpdateRabbitMq(charities);

            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        DeliveryBoys deliveryBoys=new DeliveryBoys();
        try {

            FileInputStream file = new FileInputStream(new File("./charityseed.xlsx"));
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(2);

            System.out.println("");



            for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                int j = 0;
                NumberFormat fmt = NumberFormat.getInstance(Locale.US);
                deliveryBoys.setName(workbook.getSheetAt(2).getRow(i).getCell(j).toString());
                deliveryBoys.setUsername(workbook.getSheetAt(2).getRow(i).getCell(j+1 ).toString());
                deliveryBoys.setPassword(workbook.getSheetAt(2).getRow(i).getCell(j+2 ).toString());
                deliveryBoys.setEmail(workbook.getSheetAt(2).getRow(i).getCell(j+3 ).toString());
                deliveryBoys.setMobile((long)(Double.parseDouble(workbook.getSheetAt(2).getRow(i).getCell(j + 4).toString())));
                deliveryBoys.setLicenseName(workbook.getSheetAt(2).getRow(i).getCell(j+5 ).toString());
                deliveryBoys.setLicenseNo(workbook.getSheetAt(2).getRow(i).getCell(j+6 ).toString());
                deliveryBoys.setRole(workbook.getSheetAt(2).getRow(i).getCell(j+7 ).toString());


                System.out.println(deliveryBoys);

                registrationController.saveDeliveryBoy(deliveryBoys);
                rabbitService.sendToDeliveryBoyUpdateMQ(deliveryBoys);

            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
