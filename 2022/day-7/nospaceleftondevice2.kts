import java.io.File
import java.io.InputStream
import java.math.BigInteger
import javax.swing.tree.TreeNode
import kotlin.contracts.contract
import kotlin.system.exitProcess

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

//lineList.forEach { println(it) }

data class Tree(val root: Node)

data class Node(val name: String, val parent: Node?, val children: MutableList<Node>? = null, var size: Int? = null)

var currentNode = Node("/", null, children = mutableListOf())
val tree = Tree(currentNode)

for (line in lineList) {
    println(line)
    if (line.startsWith("$")) {
        if (line.contains("cd")) {
            if (line.endsWith("/")) {
                currentNode = tree.root
            } else if (line.endsWith("..")) {
                currentNode = currentNode.parent!!
            } else {
                currentNode = currentNode.children!!.first { it.name == line.split(" ").last() && it.children != null }
            }
        } else if (line.contains("ls")) {
            continue
        }
    } else {
        val split = line.split(" ")
        if (split.first() == "dir") {
            currentNode.children!!.add(Node(split.last(), currentNode, children = mutableListOf()))
        } else {
            currentNode.children!!.add(Node(split.last(), currentNode, size = split.first().toInt()))
        }
    }
}

val minimum = 4274331
var value = 70000000

fun getSize(node: Node): Int{
    if (node.children == null) {
        return node.size!!
    } else {
        val size = node.children.sumOf { getSize(it) }
        if (size < value && size >= minimum) {
            value = size
        }
        node.size = size
        return size
    }
}

getSize(tree.root)

println(70000000 - 44274331 - 30000000)

value