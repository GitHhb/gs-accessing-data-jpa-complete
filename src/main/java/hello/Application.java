package hello;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Blob;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a couple of customers
			System.out.println("Start here ........");
//			System.out.println("1 File f exists? " + (Files.exists(‪"D:\\!Data\\Download\\Yahtzee5.jpg") ? "OK": "NOK")); 

			Customer newCust = new Customer();
//			File f = new File("‪D:\\!Data\\Download\\Yahtzee5.jpg");
			File f = new File("C:\\Data\\img.jpg");

			System.out.println("File f " + (f.exists() ? "OK": "NOK")); 
			BufferedImage bufimage = ImageIO.read(f);
			System.out.println("File f " + (f.exists() ? "OK": "NOK")); 

//			ImageOutputStream outstream = ImageIO.createImageOutputStream(bufimage);
//			
//			  BufferedInputStream fstream = new BufferedInputStream(new FileInputStream(f));
//
//			  Blob blob = connection.getConnection().createBlob();
//			  BufferedOutputStream bstream = new  BufferedOutputStream(blob.setBinaryStream(1));
//			  // stream copy runs a high-speed upload across the network
//			  StreamUtils.copy(fstream, bstream);
//
//			  FileBLOBEntity file = new FileBLOBEntity();
//
//			  file.setName("//C:/tmp/6mb_file.wmv");
//			  file.setTheData(blob);
//			  // save runs a low-speed download across the network.  this is where
//			  // Spring does the SQL insert.  For a large file, I get an OutOfMemory exception here.
			newCust.setFirstName("Pietje");
			newCust.setLastName("ZK");
			System.out.println("Before setIamge");

			ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufimage,"jpeg",out);
//            byte[] buf = out.toByteArray();
            newCust.setImage(out.toByteArray());
            System.out.println("After setIamge");
			
			  repository.save(newCust);
				System.out.println("After save newCust");

			
			repository.save(new Customer("Jack", "Bauer" ));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
            log.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
            log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : repository.findByLastName("Bauer")) {
				log.info(bauer.toString());
			}
            log.info("");
		};
	}

}
