package cleanCatClient;

public class Client {

	private static final Client INSTANCE = new Client();

	public static final Client getInstance() {
		return INSTANCE;
	}

	public void init() {
		ClientConstants.getInstance().init();
	}

	public void shutdown() {
		ClientConstants.getInstance().shutdown();
	}

}
