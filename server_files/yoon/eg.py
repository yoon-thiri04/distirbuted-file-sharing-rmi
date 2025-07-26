def divide_remain(first,second):
    """
    Calculate the quotient and remainder
    """
    return first//second, first%second

f,s=divide_remain(10,5)
print("Quotient : {}".format(f))
print("Remainder : {}".format(s))