
#############################################################################
######### class Matrix with display() function for image visualization
#############################################################################

class Matrix:
    """
    Represents a rectangular matrix with n rows and m columns.
    """

    def __init__(self, n, m, val=0):
        """
        Create an n-by-m matrix of val's.
        Inner representation: list of lists (rows)
        """
        assert n > 0 and m > 0
        #self.rows = [[val]*m]*n #why this is bad?
        self.rows = [[val]*m for i in range(n)]

    def dim(self):
        return len(self.rows), len(self.rows[0])

    def __repr__(self):
        if len(self.rows)>10 or len(self.rows[0])>10:
            return "Matrix too large, specify submatrix"
        return "<Matrix {}>".format(self.rows)

    def __eq__(self, other):
        return isinstance(other, Matrix) and self.rows == other.rows

    def copy(self):
        ''' brand new copy of matrix '''
        n,m = self.dim()
        new = Matrix(n,m)
        for i in range (n):
           for j in range (m):
               new[i,j] = self[i,j]
        return new

    # cell/sub-matrix access/assignment
    ####################################
    def __getitem__(self, ij): #ij is a tuple (i,j). Allows m[i,j] instead m[i][j]
        i,j = ij
        if isinstance(i, int) and isinstance(j, int):
            return self.rows[i][j]
        elif isinstance(i, slice) and isinstance(j, slice):
            M = Matrix(1,1) # to be overwritten
            M.rows = [row[j] for row in self.rows[i]]
            return M
        else:
            return NotImplemented

    def __setitem__(self, ij, val): #ij is a tuple (i,j). Allows m[i,j] instead m[i][j]
        i,j = ij
        if isinstance(i,int) and isinstance(j,int):
            assert isinstance(val, (int, float, complex))
            self.rows[i][j] = val
        elif isinstance(i,slice) and isinstance(j,slice):
            assert isinstance(val, Matrix)
            n,m = val.dim()
            s_rows = self.rows[i]
            assert len(s_rows) == n and len(s_rows[0][j]) == m
            for s_row, v_row in zip(s_rows,val.rows):
                s_row[j] = v_row
        else:
            return NotImplemented

    # arithmetic operations
    ########################
    def entrywise_op(self, other, op):
        if not isinstance(other, Matrix):
            return NotImplemented
        assert self.dim() == other.dim()
        n,m = self.dim()
        M = Matrix(n,m)
        for i in range(n):
            for j in range(m):
                M[i,j] = op(self[i,j], other[i,j])
        return M

    def __add__(self, other):
        return self.entrywise_op(other,lambda x,y:x+y)


    def __sub__(self, other):
        return self.entrywise_op(other,lambda x,y:x-y)
    
    def __neg__(self):
        n,m = self.dim()
        return Matrix(n,m) - self


    def __mul__(self, other):
        if isinstance(other, Matrix):
            return self.multiply_by_matrix(other)
        elif isinstance(other, (int, float, complex)):
            return self.multiply_by_scalar(other)
        else:
            return NotImplemented

    __rmul__ = __mul__
        
    def multiply_by_scalar(self, val):
        n,m = self.dim()
        return self.entrywise_op(Matrix(n,m,val), lambda x,y :x*y)
###a more efficient version, memory-wise. 
##        n,m = self.dim()
##        M = Matrix(n,m)
##        for i in range(n):
##            for j in range(m):
##                M[i,j] = self[i,j] * val
##        return M

    def multiply_by_matrix(self, other):
        assert isinstance(other, Matrix)
        n,m = self.dim()
        n2,m2 = other.dim()
        assert m == n2
        M = Matrix(n,m2)
        for i in range(n):
            for j in range(m2):
                M[i,j] = sum(self[i,k] * other[k,j] for k in range(m))
        return M


    # Input/output
    ###############
    def save(self, filename):
        f = open(filename, 'w')
        n,m = self.dim()
        print(n,m, file=f)
        for row in self.rows:
            for e in row:
                print(e, end=" ", file=f)
            print("",file=f) #newline
        f.close()

    @staticmethod
    def load(filename):
        f = open(filename)
        line = f.readline()
        n,m = [int(x) for x in line.split()]
        result = Matrix(n,m)
        for i in range(n):
            line = f.readline()
            row = [int(x) for x in line.split()]
            assert len(row) == m
            result.rows[i] = row
        return result

    # display - for image visualization - no need to understand this
    ################################################################
    def display(self, title=None, zoom=None):
        height, width = self.dim()
        pixels = " ".join('{' + ' '.join('#'+'{:02x}'.format(int(pixel))*3
                                       for pixel in row) + '}'
                          for row in self.rows)

        def tk_worker(root):
            pi = tkinter.PhotoImage(width=width,height=height)
            pi.put(pixels)
            if zoom is not None:
                assert zoom > 0
                if zoom > 1:
                    pi = pi.zoom(zoom)
                elif zoom < 1:
                    pi = pi.subsample(zoom)
            tl = tkinter.Toplevel(master=root)
            tl.title('Matrix' if title is None else title)
            lb = tkinter.Label(master=tl, image=pi)
            lb.pi = pi
            lb.pack()
            return tl

        if TKTHREAD_ENABLED:
            with _TkThread.the_lock:
                _TkThread.the_queue.append(tk_worker)
        else:
            root = tkinter.Tk()
            root.withdraw()
            tl = tk_worker(root)
            tl.protocol("WM_DELETE_WINDOW", root.destroy)
            root.mainloop()

################# More code for image visualization
# Enabled only for linux/windows machines.
import sys
import tkinter
import time

TKTHREAD_ENABLED = sys.platform in ('win32', 'linux')

if TKTHREAD_ENABLED:
    import threading

    class _TkThread(threading.Thread):
        the_lock = threading.RLock()
        the_queue = []
        
        def __init__(self, *args, **kwargs):
            threading.Thread.__init__(self, *args, **kwargs)
            self.setDaemon(True)

        def run(self):
            root = tkinter.Tk()
            root.withdraw()
            while True:
                with self.the_lock:
                    func = self.the_queue.pop() if self.the_queue else None
                if func is not None:
                    func(root)
                root.update()
                time.sleep(.1)
    _TkThread().start()


