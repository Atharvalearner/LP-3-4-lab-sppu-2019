# Name: Avadhut Jagtap
# Roll No: 29
# Class: BE

import os
from math import ceil
from io import StringIO

def left(s, w):
    """Left aligns input string in table"""
    padding = w - len(s)  # count excess room to pad
    return s + " " * padding + '|'

def center(s, w):
    """Center aligns input string in table"""
    padding = w - len(s)
    left_padding = " " * (padding // 2)
    right_padding = " " * (ceil(padding / 2))
    return left_padding + s + right_padding

def prd(x, dec_digits, width):
    """Right aligns float values with specified number of precision digits in a table"""
    return f'{x:>{width}.{dec_digits}f}'

def print_docs(state, size):
    """Prints each document at a specific iteration inside the table"""
    doc_str = ', '.join([state[i] for i in range(size) if state[i] != ""])
    return left(doc_str, 98)

def e_value(b, rj, pj):
    """Calculates E value"""
    return 1 - (((1 + b * b) * rj * pj) / (b * b * pj + rj))

# Hardcoded Rq and A
Rq = ["d3", "d5", "d9", "d25", "d39", "d44", "d56", "d71", "d89", "d123"]
A = ["d123", "d84", "d56", "d6", "d8", "d9", "d511", "d129", "d187", "d25", "d38", "d48", "d250", "d113", "d3"]

# File output for results
output_file = "Recall_Precision_Evaluation_output.txt"
write = open(output_file, "w")

# required constants and arrays for calculations
modRq = len(Rq)
Ra = [''] * len(A)
P = [0] * len(A)
R = [0] * len(A)
modRa = 0
modA = 0

# Table header formatting and printing
print("-" * (45 * 3 + 11))
write.write("-" * (45 * 3 + 11) + "\n")
print(f"|{center('Documents', 96)} | {center('|Ra|', 8)} | {center('|A|', 8)} | {center('Precision(%)', 5)}|{center('Recall(%)', 5)} |")
write.write(f"|{center('Documents', 96)} | {center('|Ra|', 8)} | {center('|A|', 8)} | {center('Precision(%)', 5)}|{center('Recall(%)', 5)} |\n")
print("-" * (45 * 3 + 11))
write.write("-" * (45 * 3 + 11) + "\n")

# Algorithm to calculate and print all the values in the output table
for i in range(len(A)):
    Ra[i] = A[i]
    modA += 1
    if A[i] in Rq:
        modRa += 1
    precision = (modRa / modA) * 100
    P[i] = precision / 100
    recall = (modRa / modRq) * 100
    R[i] = recall / 100
    
    print(print_docs(Ra, len(Ra)))
    write.write(print_docs(Ra, len(Ra)) + "\n")
    
    print(f"{prd(modRa, 2, 10)}|{prd(modA, 2, 10)}|{prd(precision, 2, 13)}|{prd(recall, 2, 10)}|")
    write.write(f"{prd(modRa, 2, 10)}|{prd(modA, 2, 10)}|{prd(precision, 2, 13)}|{prd(recall, 2, 10)}|\n")

# Closing the table
print("-" * (45 * 3 + 11))
write.write("-" * (45 * 3 + 11) + "\n")

# Taking user input for calculation of Fj and Ej
j = int(input(f"Harmonic mean and E-value\nEnter value of j(0 - {len(A) - 1}) to find F(j) and E(j): "))

# Calculating Harmonic mean and printing in table
Fj = (2 * P[j] * R[j]) / (P[j] + R[j])
print("-" * (15 * 2 + 3))
print(f"| Harmonic mean (F{j}) is: |{Fj} |")
print("-" * (15 * 2 + 3))
write.write("-" * (15 * 2 + 3) + "\n")
write.write(f"| Harmonic mean (F{j}) is: |{Fj} |\n")
write.write("-" * (15 * 2 + 3) + "\n")

# Table header for E-value
print("-" * (15 * 2 + 4))
print(f"|{center('E-Value', 32)}|")
print("-" * (15 * 2 + 4))
write.write("-" * (15 * 2 + 4) + "\n")
write.write(f"|{center('E-Value', 32)}|\n")
write.write("-" * (15 * 2 + 4) + "\n")

# Table header (sub columns)
print(f"|{center('b>1', 10)}|{center('b=0', 10)}|{center('b<1', 10)}|")
print("-" * (15 * 2 + 4))
write.write(f"|{center('b>1', 10)}|{center('b=0', 10)}|{center('b<1', 10)}|\n")
write.write("-" * (15 * 2 + 4) + "\n")

# Calculating and Printing E-Values in table
print(f"|{prd(e_value(1.1, R[j], P[j]), 2, 10)}|{prd(e_value(0, R[j], P[j]), 2, 10)}|{prd(e_value(0.9, R[j], P[j]), 2, 10)}|")
write.write(f"|{prd(e_value(1.1, R[j], P[j]), 2, 10)}|{prd(e_value(0, R[j], P[j]), 2, 10)}|{prd(e_value(0.9, R[j], P[j]), 2, 10)}|\n")

# Closing the table
print("-" * (15 * 2 + 4))
write.write("-" * (15 * 2 + 4) + "\n")

write.close()
