package src;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculator {
    
    private static final double TAX_RATE = 0.10;
    
    /**
     * 割引とポイント計算機能（完成版）
     * 
     * @param unitPrice 商品単価
     * @param quantity 数量
     * @param discountRate 割引率（0.0〜1.0）
     * @param pointRate ポイント還元率（0.0〜1.0）
     * @param couponAmount クーポン割引額
     * @param isMember 会員かどうか
     * @return 最終支払額とポイント還元額
     */
    public BigDecimal[] calculateWithDiscountAndPoints(
            int unitPrice, int quantity, double discountRate, 
            double pointRate, int couponAmount, boolean isMember) {
        
        BigDecimal price = new BigDecimal(unitPrice, java.math.MathContext.DECIMAL128);
        BigDecimal qty = new BigDecimal(quantity);
        
        // 小計
        BigDecimal subtotal = price.multiply(qty);
        
        // 割引を税抜き価格に適用
        BigDecimal discount = subtotal.multiply(new BigDecimal(discountRate));
        discount = discount.setScale(0, RoundingMode.DOWN);
        
        // 割引後小計
        BigDecimal discountedSubtotal = subtotal.subtract(discount);
        
        // クーポン適用
        if (couponAmount > 0) {
            BigDecimal coupon = new BigDecimal(couponAmount);
            coupon = coupon.min(discountedSubtotal);
            discountedSubtotal = discountedSubtotal.subtract(coupon);
        }
        
        // 会員割引（5%）
        if (isMember) {
            discountedSubtotal = discountedSubtotal.multiply(new BigDecimal("0.95"));
            discountedSubtotal = discountedSubtotal.setScale(0, RoundingMode.DOWN);
        }
        
        // 消費税計算
        BigDecimal taxRate = new BigDecimal("0.10");
        BigDecimal tax = discountedSubtotal.multiply(taxRate);
        tax = tax.setScale(0, RoundingMode.DOWN);
        
        // 税込み価格
        BigDecimal totalWithTax = discountedSubtotal.add(tax);
        
        // ポイント計算
        BigDecimal points = totalWithTax.multiply(new BigDecimal(pointRate));
        points = points.setScale(0, RoundingMode.DOWN);
        
        return new BigDecimal[] { totalWithTax, points };
    }
}
