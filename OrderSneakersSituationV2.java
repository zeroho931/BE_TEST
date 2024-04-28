package 과제12;

import java.util.*;

public class OrderSneakersSituationV2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Staff staff = new Staff();
        staff.readFileAndSetSneakerInfoMap();
        staff.readFileAndSetSneakersStockMap();

        System.out.println("매장 직원: 고객 등록을 시작합니다.");

        Set<String> registeredCustomers = new HashSet<>(); // 등록된 고객 정보 저장

        while (true) {
            System.out.println("매장 직원: 안녕하세요~, \"고객등급,이름,배송선호여부,예산,운동화모델명\" 입력해주세요");
            String response = scanner.nextLine();

            if (response.equals("끝")) {
                break; // "끝"을 입력하면 루프 종료
            }

            try {
                String[] responseArray = response.split(",");
                if (responseArray.length != 5) {
                    throw new IllegalArgumentException("입력 형식이 잘못되었습니다. 올바른 형식: VIP,Alice,true,150000,Air Max");
                }

                String customerLevelStr = responseArray[0].trim();
                String customerName = responseArray[1].trim();
                boolean prefersDelivery = Boolean.parseBoolean(responseArray[2].trim());
                long budget = Long.parseLong(responseArray[3].trim());
                String requestedModel = responseArray[4].trim();

                CustomerLevel customerLevel = CustomerLevel.valueOf(customerLevelStr.toUpperCase());

                // 고객 정보를 문자열로 저장하여 중복 체크
                String customerKey = customerName + "_" + requestedModel;
                if (registeredCustomers.contains(customerKey)) {
                    System.out.println("매장 직원: 이미 등록된 고객입니다.");
                    continue; // 중복 등록 방지
                }

                // Customer 객체 생성
                Customer customer = new Customer(customerName, customerLevel, prefersDelivery, budget, requestedModel);

                // 고객 등록
                staff.addCustomer(customer);

                // 등록된 고객 정보 저장
                registeredCustomers.add(customerKey);
            } catch (NumberFormatException e) {
                System.out.println("매장 직원: 숫자 형식에 문제가 있습니다. 다시 입력해주세요.");
                continue; // 숫자 변환 예외 발생 시 다시 입력 받기
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                System.out.println("매장 직원: " + e.getMessage());
                System.out.println("매장 직원: 다시 입력해주세요.");
                continue; // 기타 예외 발생 시 다시 입력 받기
            }
        }

        // 등록된 고객들 처리
        System.out.println("ddd");
        staff.processCustomers();

        // 매장 업무 종료
        System.out.println("매장 직원: 모든 고객 처리가 완료되었습니다. 오늘의 업무를 마감합니다.");
        scanner.close();
    }
}
