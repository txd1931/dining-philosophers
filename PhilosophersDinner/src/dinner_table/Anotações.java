package dinner_table;

public class Anotações{
/*
 * 
 * Modificar eat(), think() e putForks() [PG 136 PDF; Codigo base em C]
	0 1 2 3 4
	1 = 0/1
	2 = 1/2
	3 = 2/3
	4 = 3/4
	5 = 4/0 
	forks[i] == 0 (livre)
	forks[i] == 1 (robarão)
 * 
 * 
- Caso um processo precise de um recurso indisponivel, ele deve ser bloquado ate que o recurso esteja disponivel

Usar um arranjo, estado, para controlar se um filósofo está comendo, pensando, ou com fome (tentando conseguir garfos). 
Um filósofo pode passar para o estado comendo apenas se nenhum de seus vizinhos estiver comendo.
Os vizinhos do filósofo i são definidos pelas macros LEFT e RIGHT.
Em outras palavras, se i é 2, LEFT é 1 e RIGHT é 3.
O programa usa um conjunto de semáforos, um por filósofo, portanto os filósofos com fome podem ser bloqueados 
se os garfos necessários estiverem ocupados.
Observe que cada processo executa a rotina philosopher como seu código principal, mas as outras rotinas,
take_forks, put_forks e test, são rotinas ordinárias e não
processos separados. 	
*/	
}
