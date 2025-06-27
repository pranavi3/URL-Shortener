package com.np.urlShortener.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "url_mappings")
public class URLMappings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", unique = true, nullable = true)
    private String shortCode;

    @Column(name = "long_url", nullable = false, columnDefinition = "TEXT")
    private String longURL;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "clicks")
    private Integer clicks = 0;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public URLMappings() {
        this.creationDate = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "URLMappings{" +
                "id=" + id +
                ", shortCode='" + shortCode + '\'' +
                ", longURL='" + longURL + '\'' +
                ", userId='" + userId + '\'' +
                ", creationDate=" + creationDate +
                ", clicks=" + clicks +
                ", expiresAt=" + expiresAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
