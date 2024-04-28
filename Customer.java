package 과제12;

import java.io.FileWriter;
import java.io.IOException;

public class Customer {
    private String name;
    private CustomerLevel customerLevel;
    private boolean prefersDelivery;
    private long budget;
    private String requestedModel;

    public Customer(String name, CustomerLevel customerLevel, boolean prefersDelivery, long budget, String requestedModel) {
        this.name = name;
        this.customerLevel = customerLevel;
        this.prefersDelivery = prefersDelivery;
        this.budget = budget;
        this.requestedModel = requestedModel;
    }

    public CustomerLevel getCustomerLevel() {
        return customerLevel;
    }

    public long getBudget() {
        return budget;
    }

    public String getRequestedModel() {
        return requestedModel;
    }

    public void purchaseSneakers(SneakersInfo sneakersInfo, long totalPrice) {
        if (budget >= totalPrice) {
            System.out.println("매장 직원: " + name + "님의 구매가 완료되었습니다.");
            System.out.println("구매 모델: " + sneakersInfo.getModelName() + ", 결제 금액: " + totalPrice + "원");

            // 예산 차감
            budget -= totalPrice;

            // 배송 기록 작성
            generateDeliveryRecord(sneakersInfo);
        } else {
            System.out.println("매장 직원: " + name + "님의 예산이 부족하여 구매할 수 없습니다.");
            rejectAndExit();
        }
    }

    public void rejectAndExit() {
        System.out.println("고객 " + name + ": 다음에 올게요!");
    }

    private void generateDeliveryRecord(SneakersInfo sneakersInfo) {
        try (FileWriter writer = new FileWriter("delivery_record.txt", true)) {
            writer.write("배송 모델: " + sneakersInfo.getModelName() + "\n");
            writer.write("고객 이름: " + name + "\n");
            writer.write("배송 주소: " + (prefersDelivery ? "집 주소" : "매장 수령") + "\n");
            writer.write("====================================\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }
}
