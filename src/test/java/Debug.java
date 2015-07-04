import model.auth.Tokens;
import model.campaign.Campaigns;
import model.product.Products;
import model.user.Users;


public class Debug {

	
	public static void main(String[] args) {

		Tokens tokens = Tokens.getInstance();
		Users users = Users.getInstance();
		Products products = Products.getInstance();
		Campaigns campaigns = Campaigns.getInstance();
		
	}

}
