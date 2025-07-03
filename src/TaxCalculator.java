package src;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculator {
    
    private static final double TAX_RATE = 0.10;
    
    /**
     * 割引とポイント計算機能（初期実装）
     * 
     * @param unitPrice 商品単価
     * @param quantity 数量
     * @param discountRate 割引率（0.0〜1.0）
     * @param pointRate ポイント還元率（0.0〜1.0）
     * @return 最終支払額とポイント還元額
     */
    public double[] calculateWithDiscountAndPoints(
            int unitPrice, int quantity, double discountRate, double pointRate) {
        
        // 小計を計算
        double subtotal = unitPrice * quantity;
        
        // 税込み価格を計算
        double totalWithTax = subtotal * (1 + TAX_RATE);
        
        // 割引を適用（税込み価格に対して）
        double discountAmount = totalWithTax * discountRate;
        double discountedTotal = totalWithTax - discountAmount;
        
        // ポイント計算（税込み価格に対して）
        double points = discountedTotal * pointRate;
        
        // 端数処理
        discountedTotal = Math.floor(discountedTotal);
        points = Math.floor(points);
        
        return new double[] { discountedTotal, points };
    }
}
