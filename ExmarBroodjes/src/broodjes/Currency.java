package broodjes;

public class Currency {
    
    public static final Currency ZERO = new Currency(0);
    
    private final int amount;
    
    public Currency(int amount) {
        this.amount = amount;
    }

    public boolean largerOrEquals(Currency price) {
        return amount>=price.amount;
    }

    public Currency substract(Currency price) {
        return new Currency(amount-price.amount);
    }

    public boolean isZero() {
        return amount == 0;
    }
    
    public Currency add(Currency price) {
        return new Currency(amount+price.amount);
    }
    
    public Currency negate() {
        return new Currency(-amount);
    }
    
    public Currency multiply(Currency multiplier) {
        return new Currency(amount*multiplier.amount);
    }
    
    public Currency divide(Currency diviser) {
        return new Currency(amount/diviser.amount);
    }

    public boolean isPositive() {
        return amount>=0;
    }
    
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("€");
        if (amount<0) {
            result.append("-");
        }
        int workAmount = Math.abs(amount);
        if (workAmount<10) {
            result.append("0,0"+workAmount);
        } else if (workAmount<100) {
            result.append("0,"+workAmount);
        } else {
            String amountStr = ""+workAmount;
            result.append(amountStr.substring(0, amountStr.length()-2));
            result.append(",");
            result.append(amountStr.substring(amountStr.length()-2));
        }
        return result.toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Currency other = (Currency) obj;
        if (amount != other.amount)
            return false;
        return true;
    }


}
