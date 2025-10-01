package edu.westminsteru.cmpt355.level1.ir;

import edu.westminsteru.cmpt355.level1.ast.AstNode;
import edu.westminsteru.cmpt355.level1.util.TreeNode;

/** Base interface for all IR nodes */
public sealed interface IrNode extends TreeNode<IrNode>
    permits IrProgram, IrStatement, IrExpression {

    /** Returns the child nodes of this IR node */
    @Override
    Iterable<? extends IrNode> children();

    /** Returns a short description of this IR node */
    @Override
    String toString();

}
