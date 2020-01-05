package module;

public class Suggestion {

    private String account;
    private String suggestionContent;//建议内容
    private String contactWay;//联系方式
    private String submitTime;//提交时间

    public Suggestion(String account, String suggestionContent, String contactWay) {
        this.account = account;
        this.suggestionContent = suggestionContent;
        this.contactWay = contactWay;
    }

    public Suggestion(String account, String suggestionContent, String contactWay, String submitTime) {
        this.account = account;
        this.suggestionContent = suggestionContent;
        this.contactWay = contactWay;
        this.submitTime = submitTime;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getAccount() {
        return account;
    }

    public String getSuggestionContent() {
        return suggestionContent;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setSuggestionContent(String suggestionContent) {
        this.suggestionContent = suggestionContent;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
}
