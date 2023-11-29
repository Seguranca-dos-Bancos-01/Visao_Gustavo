from mysql.connector import connect
import psutil
import time
from datetime import datetime
import pyodbc

def mysql_connection(host,user,passwd,database=None):
    connection =  connect(
        host = host,
        user = user,
        passwd = passwd,
        database = database
    )
    return connection

connection  = mysql_connection('localhost','root','rootpassword','SecurityBank')

server = '34.206.192.7'
database = 'SecurityBank'
username = 'sa'
password = 'UrubuDoGit123'

conn = pyodbc.connect('DRIVER={SQL Server};SERVER='+server+';DATABASE='+database+';UID='+username+';PWD='+password)

cursor1AWS = conn.cursor()
cursor2AWS = conn.cursor()
cursor3AWS = conn.cursor()
cursor4AWS = conn.cursor()
cursor5AWS = conn.cursor()
cursor6AWS = conn.cursor()

mycursor1 = connection.cursor()
mycursor2 = connection.cursor()
mycursor3 = connection.cursor()
mycursor4 = connection.cursor()
mycursor5 = connection.cursor()
mycursor6 = connection.cursor()
mycursor1Comp = connection.cursor()

valoresAWS = []
valores = []

cursor1AWS = conn.cursor()
cursor2AWS = conn.cursor()
cursor3AWS = conn.cursor()
cursor4AWS = conn.cursor()
cursor5AWS = conn.cursor()
cursor6AWS = conn.cursor()
cursor1CompAWS = conn.cursor()

cursor1AWS.execute("select idMetrica from metrica where idMetrica = 1")
result1AWS = cursor1AWS.fetchall()
idMetricaAWS = [x[0] for x in result1AWS]
valoresAWS.extend(idMetricaAWS)
fkMetricaAWS = valoresAWS[0]

cursor2AWS.execute("select idServidor from servidor where idServidor = 1")
result2AWS = cursor2AWS.fetchall()
idServidorAWS = [x[0] for x in result2AWS]
valoresAWS.extend(idServidorAWS)
fkServidorAWS = valoresAWS[1]

cursor3AWS.execute("select idBanco from banco where idBanco = 1")
result3AWS = cursor3AWS.fetchall()
idBancoAWS = [x[0] for x in result3AWS]
valoresAWS.extend(idBancoAWS)
fkBancoAWS = valoresAWS[2]

cursor4AWS.execute("select idEspecificacoes from especificacao where idEspecificacoes = 1")
result4AWS = cursor4AWS.fetchall()
idEspecAWS = [x[0] for x in result4AWS]
valoresAWS.extend(idEspecAWS)
fkEspecAWS = valoresAWS[3]

cursor5AWS.execute("Select idPlano from plano_contratado where idPlano = 1")
result5AWS = cursor5AWS.fetchall()
idPlanoAWS = [x[0] for x in result5AWS]
valoresAWS.extend(idPlanoAWS)
fkPlanoAWS = valoresAWS[4]

cursor6AWS.execute("select idParticao from particao where idParticao = 2")
result6AWS = cursor6AWS.fetchall()
idParticaoAWS = [x[0] for x in result6AWS]
valoresAWS.extend(idParticaoAWS)
fkParticaoAWS = valoresAWS[5]




mycursor1.execute("SELECT idServidor FROM servidor WHERE apelido = 'Server C'")
result1 = mycursor1.fetchall()
id_servidor_vetor1 = [x[0] for x in result1]
valores.extend(id_servidor_vetor1)
fkServidor = valores[0]


mycursor2.execute("SELECT idBanco FROM banco WHERE nomeFantasia = 'Bank C'")
result2 = mycursor2.fetchall()
id_banco_vetor2 = [x[0] for x in result2]
valores.extend(id_banco_vetor2)  
fkBanco = valores[1]


mycursor3.execute("SELECT idEspecificacoes FROM especificacao WHERE idEspecificacoes = 1")
result3 = mycursor3.fetchall()
id_esp_vetor3 = [x[0] for x in result3]
valores.extend(id_esp_vetor3)
fkEspec = valores[2]

mycursor4.execute("SELECT idPlano FROM plano_contratado WHERE tipo = 1")
result4 = mycursor4.fetchall()
id_plano_vetor4 = [x[0] for x in result4]
valores.extend(id_plano_vetor4)
fkPlano = valores[3]

mycursor5.execute("Select idMetrica from metrica where idMetrica = 1")
result5 = mycursor5.fetchall()
id_metrica_vetor5 = [x[0] for x in result5]
valores.extend(id_metrica_vetor5)
fkMetrica = valores[4]

mycursor6.execute("select idParticao from particao where idParticao = 1")
result6 = mycursor6.fetchall()
id_particao_vetor6 = [x[0] for x in result6]
valores.extend(id_particao_vetor6)
fkParticao = valores[5]

queryC1 = "INSERT INTO componentes(nome, modelo, fkMetrica, fkServidor, fkBanco, fkEspecificacoes, fkPlano) VALUES ('cpu', 'cpu', %s, %s, %s, %s, %s)"
queryC1AWS = "INSERT INTO componentes(nome, modelo, fkMetrica, fkServidor, fkBanco, fkEspecificacoes, fkPlano) VALUES ('cpu', 'cpu', ?, ?, ?, ?, ?)"
valuesC1 = (fkMetrica, fkServidor, fkBanco, fkEspec, fkPlano)
valuesC1AWS = (fkMetricaAWS,fkServidorAWS,fkBancoAWS,fkEspecAWS,fkPlanoAWS)
mycursor1Comp.execute(queryC1, valuesC1)
cursor1CompAWS.execute(queryC1AWS,valuesC1AWS)
connection.commit()
conn.commit()



mycursor1CompS = connection.cursor()
mycursor1CompSAWS = conn.cursor()
mycursor1CompS.execute("select idComponentes from componentes where nome = 'cpu'")
mycursor1CompSAWS.execute("select idComponentes from componentes where nome = 'cpu'")
result1 = mycursor1CompS.fetchall()
result1AWS = mycursor1CompSAWS.fetchall()
id_componente_vetor1 = [x[0] for x in result1]
id_componente_vetor1AWS = [x[0] for x in result1AWS]
idCPUAWS = id_componente_vetor1AWS [0]
idCPU = id_componente_vetor1[0]

while True:
    cpu = psutil.cpu_percent(interval=1)

    horarioAtual = datetime.now()
    horarioFormatado = horarioAtual.strftime('%Y-%m-%d %H:%M:%S')

    cursor = connection.cursor()
    cursorAWS = conn.cursor()
    

    query = "INSERT INTO registros (dataHorario, dadoCaptado, fkServidorReg, fkBanco, fkEspecificacoes, fkComponentesReg, fkMetrica,fkPlano,fkParticao) VALUES (%s, %s, %s, %s, %s, %s, %s,%s,%s)"
    queryAWS = "INSERT INTO registros (dataHorario, dadoCaptado, fkServidorReg, fkBanco, fkEspecificacoes, fkComponentesReg, fkMetrica,fkPlano,fkParticao) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)"
    cursor.execute(query, (horarioFormatado, cpu, fkServidor, fkBanco, fkEspec, idCPU,fkMetrica,fkPlano,fkParticao))
    cursorAWS.execute(queryAWS,(horarioFormatado, cpu, fkServidorAWS, fkBancoAWS, fkEspecAWS, idCPUAWS,fkMetricaAWS,fkPlanoAWS,fkParticaoAWS))
    connection.commit()
    conn.commit()
    
    time.sleep(10)
