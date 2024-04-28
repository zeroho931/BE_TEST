package 과제12;

public enum CustomerLevel {
    VIP("vip 등급 고객"),
    GOLD("gold 등급 고객"),
    SILVER("silver 등급 고객");

    private final String koreanName;

    CustomerLevel(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    @Override
    public String toString() {
        return name();
    }
}
