package ca.sheridancollege;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import ca.sheridancollege.beans.Toy;
import ca.sheridancollege.database.DatabaseAccess;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestController {

    //@Autowired any necessary repositories 
 	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private DatabaseAccess databaseAccess;


	//testing index page
	@Test
    public void testLoadingHomePage() throws Exception {
    		this.mockMvc.perform(get("/")) 
    			.andExpect(status().isOk())
    			.andExpect(view().name("index.html"));		
    }
	
	//testing searching name
	@Test
    public void testSearchingByName() throws Exception {
    		LinkedMultiValueMap<String, String> requestParams 
    			= new LinkedMultiValueMap<>();
    		requestParams.add("name", "Woody");

    		this.mockMvc
			.perform(get("/searchByName").params(requestParams)) 
    			.andExpect(status().isOk())
    			.andExpect(view().name("search/result.html"));		
    }
	
	//testing searchng price
	@Test
    public void testSearchingByPrice() throws Exception {
    		LinkedMultiValueMap<String, String> requestParams 
    			= new LinkedMultiValueMap<>();
    		requestParams.add("min", "10.00");
    		requestParams.add("max", "90.00");

    		this.mockMvc
			.perform(get("/searchByPrice").params(requestParams)) 
    			.andExpect(status().isOk())
    			.andExpect(view().name("search/result.html"));		
    }
	
	
	//testing searching quantity
	@Test
    public void testSearchingByQuantity() throws Exception {
    		LinkedMultiValueMap<String, String> requestParams 
    			= new LinkedMultiValueMap<>();
    		requestParams.add("min", "50");
    		requestParams.add("max", "150");

    		this.mockMvc
			.perform(get("/searchByQuantity").params(requestParams)) 
    			.andExpect(status().isOk())
    			.andExpect(view().name("search/result.html"));		
    }
	
	//test if adding toy works
    @Test
    public void testAddingToysDA() {
        Toy toy = new Toy();
        int originalsize = databaseAccess.getToys().size();
        
        databaseAccess.addToy(toy);
        int foundsize = databaseAccess.getToys().size();
     
        // test size
        assertThat(foundsize).isEqualTo(originalsize+1);
    }
    
  //test if deleting toy works
    @Test
    public void testDeletingToysDA() {
        int id = 3;
        int originalsize = databaseAccess.getToys().size();
        
        databaseAccess.deleteToy(id);
        int foundsize = databaseAccess.getToys().size();
     
        // test size
        assertThat(foundsize).isEqualTo(originalsize-1);
    }
}

