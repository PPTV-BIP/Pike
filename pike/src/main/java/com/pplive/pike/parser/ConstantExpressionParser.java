package com.pplive.pike.parser;

import java.util.ArrayList;

import com.pplive.pike.expression.AbstractExpression;
import com.pplive.pike.expression.ConstantExpression;


import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BooleanValue;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.statement.select.SubSelect;

class ConstantExpressionParser implements ExpressionVisitor {

	protected final Expression _expression;	
	protected AbstractExpression _parsedExpr;
	
	protected ArrayList<Exception> _parseErrors = new ArrayList<Exception>();
	protected void addError(Exception e){
		this._parseErrors.add(e);
	}

	public ConstantExpressionParser(Expression expression) {
		assert expression != null;
		this._expression = expression;
	}
	
	public AbstractExpression parse() {
		this._parsedExpr = null;
		this._expression.accept(this);
		if (this._parseErrors.size() > 0){
			throw new ParseErrorsException(this._parseErrors);
		}
		return this._parsedExpr;
	}
	
	// visit methods of ExpressionVisitor
	public void visit(NullValue nullValue) {
		this._parsedExpr = new ConstantExpression(null);
	}
	
	public void visit(Function function) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(InverseExpression inverseExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(JdbcParameter jdbcParameter) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}

	public void visit(BooleanValue booleanValue) {
		boolean val = booleanValue.value();
		this._parsedExpr = new ConstantExpression(Boolean.valueOf(val));
	}
	
	public void visit(DoubleValue doubleValue) {
		double val = doubleValue.getValue();
		this._parsedExpr = new ConstantExpression(Double.valueOf(val));
	}
	
	public void visit(LongValue longValue) {
		long val = longValue.getValue();
		if (val >= Integer.MIN_VALUE && val <= Integer.MAX_VALUE)
			this._parsedExpr = new ConstantExpression(Integer.valueOf((int)val));
		else
			this._parsedExpr = new ConstantExpression(Long.valueOf(val));
	}
	
	public void visit(DateValue dateValue) {
		this._parsedExpr = new ConstantExpression(dateValue.getValue());
	}
	
	public void visit(TimeValue timeValue) {
		this._parsedExpr = new ConstantExpression(timeValue.getValue());
	}
	
	public void visit(TimestampValue timestampValue) {
		this._parsedExpr = new ConstantExpression(timestampValue.getValue());
	}
	
	public void visit(Parenthesis parenthesis) {
		Expression expr = parenthesis.getExpression();
		assert expr != null;
		expr.accept(this);
	}
	
	public void visit(StringValue stringValue) {
		this._parsedExpr = new ConstantExpression(stringValue.getValue());
	}
		
	public void visit(Addition addition) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(Division division) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(Multiplication multiplication) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(Subtraction subtraction) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(AndExpression andExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(OrExpression orExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(Between between) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(EqualsTo equalsTo) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(GreaterThan greaterThan) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(GreaterThanEquals greaterThanEquals) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(InExpression inExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(IsNullExpression isNullExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(LikeExpression likeExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(MinorThan minorThan) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(MinorThanEquals minorThanEquals) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(NotEqualsTo notEqualsTo) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(net.sf.jsqlparser.schema.Column tableColumn) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(SubSelect subSelect) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
		
	public void visit(CaseExpression caseExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(WhenClause whenClause) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(ExistsExpression existsExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(AllComparisonExpression allComparisonExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(AnyComparisonExpression anyComparisonExpression) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(Concat concat) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(Matches matches) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(BitwiseAnd bitwiseAnd) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(BitwiseOr bitwiseOr) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
	
	public void visit(BitwiseXor bitwiseXor) {
		addError(new IllegalStateException("should never happen"));
		this._parsedExpr = null;
	}
}
