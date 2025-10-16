package org.example.finaldivar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListingDatabase {
    private static ListingDatabase instance;
    private final Map<String, User> users;
    private final List<Listing> listings;
    private int nextListingId;

    private ListingDatabase() {
        users = new HashMap<>();
        listings = new ArrayList<>();
        nextListingId = 1;

        // Add a default admin user
        users.put("admin", new User("admin", "admin123", "09123456789", "admin@divar.ir"));

        // Add some sample listings
        addListing(new Listing(
                nextListingId++,
                "آپارتمان 80 متری در تهرانپارس",
                "آپارتمان نوساز، دو خوابه، پارکینگ و انباری، نزدیک به مترو",
                1200000000,
                "املاک",
                "تهران، تهرانپارس",
                "09123456789",
                "admin",
                ""
        ));

        addListing(new Listing(
                nextListingId++,
                "گوشی سامسونگ Galaxy S21",
                "گوشی در حد نو، با گارانتی، رنگ مشکی، حافظه 128 گیگابایت",
                12000000,
                "کالای دیجیتال",
                "تهران، ونک",
                "09123456789",
                "admin",
                ""
        ));
    }

    public static synchronized ListingDatabase getInstance() {
        if (instance == null) {
            instance = new ListingDatabase();
        }
        return instance;
    }

    public void addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("این نام کاربری قبلاً ثبت شده است");
        }
        users.put(user.getUsername(), user);
    }

    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public void addListing(Listing listing) {
        listings.add(listing);
    }

    public List<Listing> getAllListings() {
        return new ArrayList<>(listings);
    }

    public List<Listing> searchListings(String query, String category) {
        return listings.stream()
                .filter(listing -> (category == null || listing.getCategory().equals(category)))
                .filter(listing -> (query == null || query.isEmpty() ||
                        listing.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        listing.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        listing.getLocation().toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList());
    }

    public int getNextListingId() {
        return nextListingId++;
    }

    public User getUser(String username) {
        return users.get(username);
    }
}
