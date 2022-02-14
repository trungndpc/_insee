package vn.insee.admin.retailer.controller.dto.dashboard;

public class CountUserDTO {
    private long numUser;
    private long numApprovedUser;
    private long numWaitingActiveUser;
    private long numWaitingReviewUser;

    public long getNumUser() {
        return numUser;
    }

    public void setNumUser(long numUser) {
        this.numUser = numUser;
    }

    public long getNumApprovedUser() {
        return numApprovedUser;
    }

    public void setNumApprovedUser(long numApprovedUser) {
        this.numApprovedUser = numApprovedUser;
    }

    public long getNumWaitingActiveUser() {
        return numWaitingActiveUser;
    }

    public void setNumWaitingActiveUser(long numWaitingActiveUser) {
        this.numWaitingActiveUser = numWaitingActiveUser;
    }

    public long getNumWaitingReviewUser() {
        return numWaitingReviewUser;
    }

    public void setNumWaitingReviewUser(long numWaitingReviewUser) {
        this.numWaitingReviewUser = numWaitingReviewUser;
    }
}
