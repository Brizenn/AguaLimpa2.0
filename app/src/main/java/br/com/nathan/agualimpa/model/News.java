package br.com.nathan.agualimpa.model;

public class News {

    private String title;
    private String description;
    private String category;

    // Construtor
    public News(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    // Getters e Setters (opcional, dependendo das necessidades)
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
}
