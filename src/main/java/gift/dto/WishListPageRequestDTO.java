package gift.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class WishListPageRequestDTO {
    @Min(0)
    private int page;

    @Min(1)
    @Max(30)
    private int size;

    private String sortBy;
    private String direction;

    public WishListPageRequestDTO() {}

    public WishListPageRequestDTO(int page, int size, String sortBy, String direction) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.direction = direction;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}