package src;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculator {
    
    private static final double TAX_RATE = 0.10;
    
    /**
     * 割引とポイント計算機能（BigDecimal対応版）
     * 
     * @param unitPrice 商品単価
     * @param quantity 数量
     * @param discountRate 割引率（0.0〜1.0）
     * @param pointRate ポイント還元率（0.0〜1.0）
     * @return 最終支払額とポイント還元額
     */
    public BigDecimal[] calculateWithDiscountAndPoints(
            int unitPrice, int quantity, double discountRate, double pointRate) {
        
        // BigDecimalで高精度計算
        BigDecimal price = new BigDecimal(unitPrice, java.math.MathContext.DECIMAL128);
        BigDecimal qty = new BigDecimal(quantity);
        BigDecimal taxRateBD = new BigDecimal("0.10");
        
        // 小計を計算
        BigDecimal subtotal = price.multiply(qty);
        
        // 税込み価格を計算
        BigDecimal totalWithTax = subtotal.multiply(BigDecimal.ONE.add(taxRateBD));
        
        // 割引を適用（税込み価格に対して）
        BigDecimal discount = totalWithTax.multiply(new BigDecimal(discountRate));
        BigDecimal discountedTotal = totalWithTax.subtract(discount);
        
        // ポイント計算
        BigDecimal points = discountedTotal.multiply(new BigDecimal(pointRate));
        
        // 端数処理
        discountedTotal = discountedTotal.setScale(0, RoundingMode.DOWN);
        points = points.setScale(0, RoundingMode.DOWN);
        
        return new BigDecimal[] { discountedTotal, points };
    }
}
