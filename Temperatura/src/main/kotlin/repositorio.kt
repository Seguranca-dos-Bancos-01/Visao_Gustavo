import org.springframework.jdbc.core.JdbcTemplate

class repositorio {

    lateinit var jdbctemplate:JdbcTemplate

    fun iniciar(){
        jdbctemplate = Conexao.jdbcTemplate!!
    }

    fun servidor():Int{
        val servidor = jdbctemplate.queryForObject("""
           SELECT idServidor FROM servidor WHERE apelido = 'Server C'
        """,Int::class.java)
        return servidor
    }
    fun banco():Int{
        val banco = jdbctemplate.queryForObject("""
            SELECT idBanco FROM banco WHERE nomeFantasia = 'Bank C'
        """,Int::class.java)
        return banco
    }
    fun especificacoes():Int{
        val espec = jdbctemplate.queryForObject("""
            SELECT idEspecificacoes FROM especificacao WHERE idEspecificacoes = 1
        """,Int::class.java)
        return espec
    }
    fun plano():Int{
        val plano = jdbctemplate.queryForObject("""
           SELECT idPlano FROM plano_contratado WHERE tipo = 1 
        """,Int::class.java)
        return plano
    }

    fun particao():Int{
        val particao = jdbctemplate.queryForObject("""
           SELECT idParticao FROM particao WHERE idParticao = 1 
        """,Int::class.java)
        return particao
    }

    fun metrica():Int{
        val metrica = jdbctemplate.queryForObject("""
           SELECT idMetrica FROM metrica WHERE idMetrica = 4 
        """,Int::class.java)
        return metrica
    }
    fun cadastrarComp(fkServidor:Int,fkBanco:Int,fkEspec:Int,fkPlano:Int,fkMetrica:Int){
        jdbctemplate.execute("""
           insert into componentes values
            (null,'Temperatura CPU','Temperatura',$fkMetrica,$fkServidor,$fkBanco,$fkEspec,$fkPlano)
        """)
    }
    fun getIdTemp():Int{
        val idTemp = jdbctemplate.queryForObject("""
           select min(idComponentes) from componentes where nome = "Temperatura CPU" 
        """,Int::class.java)
        return idTemp
    }

    fun add(novaTemp:Temperatura,fkServidor:Int,fkBanco:Int,fkEspec:Int,fkComponente:Int,fkMetrica: Int,fkPlano:Int,fkParticao:Int){
        jdbctemplate.execute("""
            insert into registros values (null,'${novaTemp.dtHora}','${novaTemp.dado}',$fkServidor,$fkBanco,$fkEspec,$fkComponente,$fkMetrica,$fkPlano,$fkParticao)
        """)
    }
}