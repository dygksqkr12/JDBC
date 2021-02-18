package product.view;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import product.controller.ProductController;
import product.model.vo.Stock;

public class ProductStock {
	private ProductController productController = new ProductController();
	private Scanner sc = new Scanner(System.in);
	
	public void productMenu() {
		String menu = "***** 상품재고관리프로그램 *****\n"
					+ "1. 전체상품조회\n"  
					+ "2. 상품아이디검색\n"  
					+ "3. 상품명검색\n"  
					+ "4. 상품추가\n" 
					+ "5. 상품정보변경\n"  
					+ "6. 상품삭제\n" 
					+ "7. 상품입/출고 메뉴\n"  
					+ "9. 프로그램종료\n"
					+ "-------------------------\n"
					+ "선택 : ";
		
		do {
			System.out.print(menu);
			String choice = sc.next();
			switch (choice) {
			//전체상품조회
			case "1" :
				List<Stock> list = productController.selectAll();
				displayProductList(list);
				break;
			//상품아이디검색
			case "2" :
				break;
			//상품명검색
			case "3" :
				break;
			//상품추가
			case "4" :
				break;
			//상품정보변경
			case "5" :
				break;
			//상품삭제
			case "6" :
				break;
			//상품입/출고메뉴
			case "7" :
				break;
			//프로그램종료
			case "9" :
				System.out.print("정말 종료하시겠습니까?(y/n) : ");
				char end = sc.next().toUpperCase().charAt(0);
				if(end == 'Y')
					return;
				break;
			default: System.out.println("잘못입력하셨습니다.");
				break;
			}
		} while (true);
	}

	private void displayProductList(List<Stock> list) {
		if(list != null) {
			Iterator<Stock> iter = list.iterator();
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
			
		}
	}

}
