# Функція для обертання рядка
def reverse_string(text):
    return text[::-1]

# Приклад використання
original = input("Введи рядок: ")
reversed_text = reverse_string(original)
print("Обернений рядок:", reversed_text)
