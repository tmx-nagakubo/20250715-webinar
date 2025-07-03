package src;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculator {
    
    private static final double TAX_RATE = 0.10;
    
    /**
     * 割引とポイント計算機能（税法準拠版）
     * 
     * @param unitPrice 商品単価
     * @param quantity 数量
     * @param discountRate 割引率（0.0〜1.0）
     * @param pointRate ポイント還元率（0.0〜1.0）
     * @return 最終支払額とポイント還元額
     */
    public BigDecimal[] calculateWithDiscountAndPoints(
            int unitPrice, int quantity, double discountRate, double pointRate) {
        
        BigDecimal price = new BigDecimal(unitPrice, java.math.MathContext.DECIMAL128);
        BigDecimal qty = new BigDecimal(quantity);
        
        // 小計（税抜き）
        BigDecimal subtotal = price.multiply(qty);
        
        // 割引を税抜き価格に適用
        BigDecimal discount = subtotal.multiply(new BigDecimal(discountRate));
        discount = discount.setScale(0, RoundingMode.DOWN);
        
        // 割引後小計
        BigDecimal discountedSubtotal = subtotal.subtract(discount);
        
        // 消費税計算
        BigDecimal taxRate = new BigDecimal("0.10");
        BigDecimal tax = discountedSubtotal.multiply(taxRate);
        tax = tax.setScale(0, RoundingMode.DOWN);
        
        // 税込み価格
        BigDecimal totalWithTax = discountedSubtotal.add(tax);
        
        // ポイント計算（税込み価格に対して）
        BigDecimal points = totalWithTax.multiply(new BigDecimal(pointRate));
        points = points.setScale(0, RoundingMode.DOWN);
        
        return new BigDecimal[] { totalWithTax, points };
    }
}
