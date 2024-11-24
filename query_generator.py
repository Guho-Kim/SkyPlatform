from datetime import datetime, timedelta
import random

# 날짜 범위 설정
start_date = datetime(2025, 1, 1)
end_date = datetime(2025, 1, 31)

# 항공사 목록
airlines = ['대한항공', '아시아나항공', '티웨이항공', '제주항공', '진에어항공']

# INSERT 쿼리 시작
query = "INSERT INTO Flight (departure_location, arrival_location, departure_date, arrival_date, airline, price) VALUES\n"

rows = []
current_date = start_date

while current_date <= end_date:
    # 랜덤으로 4개의 항공사를 선택
    daily_airlines = random.sample(airlines, 4)
    
    for airline in daily_airlines:
        # 출발 및 도착 시간 생성
        departure_to_ny = current_date.strftime("%Y-%m-%d %H:%M")
        arrival_to_ny = (current_date + timedelta(hours=random.randint(10, 15))).strftime("%Y-%m-%d %H:%M")
        return_from_ny = (current_date + timedelta(days=1, hours=random.randint(18, 22))).strftime("%Y-%m-%d %H:%M")
        arrival_from_ny = (current_date + timedelta(days=2, hours=random.randint(5, 8))).strftime("%Y-%m-%d %H:%M")
        
        # 가격을 랜덤으로 설정 (1,000,000 ~ 2,000,000, 1만 원 단위)
        price_to_ny = random.randint(100, 200) * 10000
        price_from_ny = random.randint(100, 200) * 10000
        
        # 데이터 추가
        rows.append(
            f"('인천', '뉴욕', '{departure_to_ny}', '{arrival_to_ny}', '{airline}', {price_to_ny})"
        )
        rows.append(
            f"('뉴욕', '인천', '{return_from_ny}', '{arrival_from_ny}', '{airline}', {price_from_ny})"
        )
    
    # 하루 증가
    current_date += timedelta(days=1)

# 쿼리 조합
query += ",\n".join(rows) + ";"

# 출력
print(query)

