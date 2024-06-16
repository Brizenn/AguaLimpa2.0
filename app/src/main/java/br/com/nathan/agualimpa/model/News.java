package br.com.nathan.agualimpa.model;

public class News {
    private String title;
    private String description;
    private String category;
    private int imageResourceId; // Adicionado

    public News(String title, String description, String category, int imageResourceId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageResourceId = imageResourceId; // Adicionado
    }

    // Getters e setters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public int getImageResourceId() { return imageResourceId; } // Adicionado
}
