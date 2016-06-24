package com.microl.core.cache;

public class BasicCacheTest {

	public static void main(String[] args) {
		BasicCache<String, Cacheable> userCache = new BasicCache<String, Cacheable>();
		
		UserProfile vietlkProfile = new UserProfile(1L, Boolean.TRUE, "VietLK is a technical leader at Vietsoft.");
		User vietlk = new User(1L, "vietlk", "Le Khac Viet", "kv.netcoding@gmail.com", vietlkProfile);
		
		UserProfile phucbtProfile = new UserProfile(1L, Boolean.FALSE, "PhucBT is a administration officer at Tan Hiep Phat.");
		User phucbt = new User(2L, "phucbt", "Bui Thi Phuc", "phucbt@savi.com.vn", phucbtProfile);
		
		UserProfile kemProfile = new UserProfile(1L, Boolean.FALSE, "TamLTM is a girl. She is a daughter of Phuc and Viet");
		User kem = new User(3L, "tamltm", "Le Thi Minh Tam", "tamltm@plusyou.us", kemProfile);
		
		
		if(userCache.isExisted("vietlk")) {
			System.out.println("Vietlk is existed. Get the info");
			Cacheable vietlk1 = userCache.get("vietlk");
			System.out.println(printUser(vietlk1));
		} else {
			System.out.println("Vietlk is not existed. Add Vietlk into the cache.");
			userCache.put("vietlk", vietlk);
			System.out.println("Cache size = " + userCache.getCache().size());
			Cacheable vietlk1 = userCache.get("vietlk");
			System.out.println(printUser(vietlk1));
		}
		
		if(userCache.isExisted("phucbt")) {
			System.out.println("PhucBT is existed. Get the info");
			Cacheable phucbt1 = userCache.get("phucbt");
			System.out.println(printUser(phucbt1));
		} else {
			System.out.println("PhucBT is not existed. Add PhucBT into the cache.");
			userCache.put("phucbt", phucbt);
			System.out.println("Cache size = " + userCache.getCache().size());
			Cacheable phucbt1 = userCache.get("phucbt");
			System.out.println(printUser(phucbt1));
		}
		
		System.out.println("Hit Miss Ratio = " + userCache.hitmiss());
		System.out.println("Hit count = " + userCache.getHitCount());
		System.out.println("Miss count = " + userCache.getMissCount());
	}
	
	private static String printUser(Cacheable object) {
		StringBuilder strBuilder = new StringBuilder();
		
		if(object instanceof User) {
			User user = (User) object;
			strBuilder.append("ID : " + user.getId() + "\n");
			strBuilder.append("Username : " + user.getUsername() + "\n");
			strBuilder.append("Name : " + user.getName() + "\n");
			strBuilder.append("Email : " + user.getEmail() + "\n");
			strBuilder.append("Introduction : " + user.getProfile().getIntroduction() + "\n");
		}
		
		return strBuilder.toString();
	}
	

}
