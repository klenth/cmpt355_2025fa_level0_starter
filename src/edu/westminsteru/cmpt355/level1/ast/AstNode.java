package edu.westminsteru.cmpt355.level1.ast;

import edu.westminsteru.cmpt355.level1.util.TreeNode;
import java.lang.Iterable;

/** Base interface for all AST nodes */
public sealed interface AstNode extends TreeNode<AstNode>
    permits AstProgram, AstStatement, AstExpression
{
    /** Returns the child nodes of this AST node */
    @Override
    Iterable<? extends AstNode> children();

    /** Returns a short description of this AST node */
    @Override
    String toString();
}
