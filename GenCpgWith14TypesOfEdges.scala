import io.shiftleft.semanticcpg.utils.MemberAccess
def toCfgNode(node: StoredNode): CfgNode = {
  node match {
    case node: Identifier        => node.parentExpression.get
    case node: MethodRef         => node.parentExpression.get
    case node: Literal           => node.parentExpression.get
    case node: MethodParameterIn => node.method
    case node: MethodParameterOut =>
      node.method.methodReturn
    case node: Call if MemberAccess.isGenericMemberAccessName(node.name) =>
      node.parentExpression.get
    case node: CallRepr     => node
    case node: MethodReturn => node
    case node: Expression   => node
  }
}
def escape(str: String): String = {
  if (str == null) {
    ""
  } else {
    str.replace("\"", "\\\"")
  }
}
def stringRepr(vertex: StoredNode): String = {
  escape(
    vertex match {
      case call: Call => (call.name, call.code).toString
      case expr: Expression =>
        (expr.label, expr.code, toCfgNode(expr).code).toString
      case method: Method           => (method.label, method.name).toString
      case ret: MethodReturn        => (ret.label, ret.typeFullName).toString
      case param: MethodParameterIn => ("PARAM", param.code).toString
      case local: Local =>
        (local.label, s"${local.code}: ${local.typeFullName}").toString
      case target: JumpTarget => (target.label, target.name).toString
      case _                  => ""
    }
  )
}
def doExport(outFileName: String) = {
  val header = s"""digraph cpggraph {  \n"""

  val nodes = cpg.graph.nodes
    .map { node =>
      s""""${node.id}" [label = "${stringRepr(
        node.asInstanceOf[StoredNode]
      )}" ]""".stripMargin
    }
    .l
    .mkString("\n")
  val edges = cpg.graph.edges
    .map { edge =>
      val labelStr = s""" [ label = "${edge.label}": ] """
      s"""  "${edge.inNode.id}" -> "${edge.outNode.id}" """ + labelStr
    }
    .l
    .mkString("\n")
  val footer = "\n}\n"

  header ++ nodes ++ "\n" ++ edges ++ footer |> outFileName
}
doExport("/home/dofl-kali/Toolkit/out/graph.dot")