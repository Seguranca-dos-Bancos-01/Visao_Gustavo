import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject

class repositorioAWS {

    lateinit var jdbcTemplate: JdbcTemplate

    fun iniciar(){
        jdbcTemplate = ConexaoAWS.jdbcTemplate!!
    }

    fun getIdBancoFunc():Int{
        val idBancoFunc = jdbcTemplate.queryForObject("""
            select idBanco from banco where idBanco = 1
        """,Int::class.java)
        return idBancoFunc
    }
    fun getIdEscalonamentoFunc():Int{
        val idEscalonamentoFunc = jdbcTemplate.queryForObject("""
            select idEscalonamento from escalonamento_funcionario where idEscalonamento = 1
        """,Int::class.java)
        return idEscalonamentoFunc
    }
    fun getIdBanco(idBanco:Int):Int{
        val banco = jdbcTemplate.queryForObject("""
            select idBanco from banco where idBanco = $idBanco
        """,Int::class.java)
        return banco
    }
    fun getIdEscalonamento(idEscalonamento:Int):Int{
        val escalonamento = jdbcTemplate.queryForObject("""
           select idEscalonamento from escalonamento_funcionario where idEscalonamento = $idEscalonamento        
        """,Int::class.java)
        return escalonamento
    }fun getIdServidor(idBanco: Int):Int{
        val servidor = jdbcTemplate.queryForObject("""
                    select min(idServidor) from servidor join banco on fkBanco = idBanco where fkBanco = $idBanco group by fkBanco;
        """,Int::class.java)
        return servidor
    }
    fun getIdMetrica():Int{
        val metrica = jdbcTemplate.queryForObject("""
            select idMetrica from metrica where idMetrica = 1
        """,Int::class.java)
        return metrica
    }

    fun getIdEspecificacao(idServidor:Int):Int{
        val especificacao = jdbcTemplate.queryForObject("""
        select fkEspecificacoes from servidor join especificacao on fkEspecificacoes = idEspecificacoes where idServidor = ${idServidor};
        """,Int::class.java)
        return especificacao
    }
    fun getIdPlano(idServidor:Int):Int{
        val plano = jdbcTemplate.queryForObject("""
            select fkPlano from servidor join plano_contratado on fkPlano = idPlano where idServidor = $idServidor;
        """,Int::class.java)
        return plano
    }
    fun getIdParticao(idBanco: Int):Int{
        val particao = jdbcTemplate.queryForObject("""
        select min(idParticao) from particao join banco on fkBanco = idBanco where fkBanco = $idBanco group by fkBanco;
        """,Int::class.java)
        return particao
    }
    fun cadastrarComp(fkServidorAWS:Int,fkBancoAWS:Int,fkEspecAWS:Int,fkPlanoAWS:Int,fkMetricaAWS:Int){
        jdbcTemplate.execute("""
           insert into componentes (nome,modelo,fkMetrica,fkServidor,fkBanco,fkEspecificacoes,fkPlano)values
            ('Temperatura CPU','Temperatura',$fkMetricaAWS,$fkServidorAWS,$fkBancoAWS,$fkEspecAWS,$fkPlanoAWS)
        """)
    }
    fun getIdTemp():Int{
        val idTemp = jdbcTemplate.queryForObject("""
           select min(idComponentes) from componentes where nome = 'Temperatura CPU' 
        """,Int::class.java)
        return idTemp
    }

    fun add(novaTemp:Temperatura,fkServidorAWS: Int,fkBancoAWS: Int,fkEspecificacoesAWS:Int,fkComponenteAWS:Int,fkMetricaAWS:Int,fkPlanoAWS: Int,fkParticaoAWS:Int){
        jdbcTemplate.execute("""
            insert into registros (dataHorario,dadoCaptado,fkServidorReg,fkBanco,fkEspecificacoes,fkComponentesReg,fkMetrica,fkPlano,fkParticao)
            values ('${novaTemp.dtHora}',${novaTemp.dado},$fkServidorAWS,$fkBancoAWS,$fkEspecificacoesAWS,$fkComponenteAWS,$fkMetricaAWS,$fkPlanoAWS,$fkParticaoAWS)
        """)
    }
}