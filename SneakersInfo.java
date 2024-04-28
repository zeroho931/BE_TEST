package 과제12;

public class SneakersInfo {
    private String modelName;
    private long price;
    private String features;

    public SneakersInfo(String modelName, long price, String features) {
        this.modelName = modelName;
        this.price = price;
        this.features = features;
    }

    public String getModelName() {
        return modelName;
    }

    public long getPrice() {
        return price;
    }

    public String getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        return "운동화 모델: " + modelName + ", 가격: " + price + "원";
    }
}
