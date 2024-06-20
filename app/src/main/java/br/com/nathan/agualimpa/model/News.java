package br.com.nathan.agualimpa.model;

public class News {
    private String title;
    private String description;
    private String category;
    private int imageResource;

    public News() {
        // Construtor sem argumentos necessário para o Firestore
    }

    public News(String title, String description, String category, int imageResource) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
