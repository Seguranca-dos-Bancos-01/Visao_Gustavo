import com.github.britooo.looca.api.core.Looca
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {

    val repositorio = repositorio()
    val repositorioAWS = repositorioAWS()
    repositorio.iniciar()
    repositorioAWS.iniciar()

    val fkServidor = repositorio.servidor()
    val fkBanco = repositorio.banco()
    val fkEspec = repositorio.especificacoes()
    val fkPlano = repositorio.plano()
    val fkParticao = repositorio.particao()
    val fkMetrica = repositorio.metrica()

    val fkBancoFunc = repositorioAWS.getIdBancoFunc()
    val fkEscalonamentoFunc = repositorioAWS.getIdEscalonamentoFunc()
    val fkBancoAWS = repositorioAWS.getIdBanco(fkBancoFunc)
    val fkServidorAWS = repositorioAWS.getIdServidor(fkBancoFunc)
    val fkMetricaAWS = repositorioAWS.getIdMetrica()
    val fkEspecificacoesAWS = repositorioAWS.getIdEspecificacao(fkServidorAWS)
    val fkPlanoAWS = repositorioAWS.getIdPlano(fkServidorAWS)
    val fkParticaoAWS = repositorioAWS.getIdParticao(fkBancoAWS)

    repositorioAWS.cadastrarComp(fkServidorAWS,fkBancoAWS,fkEspecificacoesAWS,fkPlanoAWS,fkMetricaAWS)
    repositorio.cadastrarComp(fkServidor,fkBanco,fkEspec,fkPlano,fkMetrica)
    while(true) {
        val looca = Looca()
        val temperatura = looca.temperatura.temperatura
        println("Temp (°C): $temperatura")

        val dataHoraAtual = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dataHoraFormatada = dataHoraAtual.format(formatter)
        val novaTemp = Temperatura()
        novaTemp.dado = temperatura
        novaTemp.dtHora = dataHoraFormatada

        val fkComponente = repositorio.getIdTemp()
        val fkComponenteAWS = repositorioAWS.getIdTemp()
        repositorioAWS.add(novaTemp,fkServidorAWS,fkBancoAWS,fkEspecificacoesAWS,fkComponenteAWS,fkMetricaAWS,fkPlanoAWS,fkParticaoAWS)
        repositorio.add(novaTemp,fkServidor,fkBanco,fkEspec,fkComponente,fkMetrica,fkPlano,fkParticao)

        Thread.sleep(5000)
        }
}